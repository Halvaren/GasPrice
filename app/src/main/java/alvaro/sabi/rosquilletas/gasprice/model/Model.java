package alvaro.sabi.rosquilletas.gasprice.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.GasDao;
import alvaro.sabi.rosquilletas.gasprice.model.database.GasDatabase;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;
import androidx.room.Room;

public class Model {

    private static Model instance;
    private final Resources resources;

    private GasQueries gasQueries;

    private GasDatabase database;
    private GasDao dao;

    private Town[] townsList;

    private Town selectedTown;

    private GasType selectedGas;

    private String databaseError = "Error in the access to the database";

    private Model(Context context){
        gasQueries = new GasQueries(context);
        resources = context.getResources();

        database = Room.databaseBuilder(context, GasDatabase.class, "database-name").build();
        dao = database.gasDao();
    }

    public static Model getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Model(context);
        }
        return instance;
    }

    public void getCommunities(final Response.Listener<Community[]> response, final Response.Listener<String> errorResponse, boolean checkDatabaseEmpty)
    {
        if(checkDatabaseEmpty)
        {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    return dao.numberOfRows();
                }

                @Override
                protected void onPostExecute(Integer rows) {
                    super.onPostExecute(rows);
                    if(rows == 0)
                    {
                        initializeDatabase(response, errorResponse);
                    }
                    else
                    {
                        getCommunities(response, errorResponse, false);
                    }
                }
            }.execute();
        }
        else
        {
            new AsyncTask<Void, Void, Community[]>() {
                @Override
                protected Community[] doInBackground(Void... voids) {
                    return dao.loadAllCommunities();
                }

                @Override
                protected void onPostExecute(Community[] communities) {
                    super.onPostExecute(communities);
                    if(communities == null || communities.length == 0)
                    {
                        errorResponse.onResponse(databaseError);
                    }
                    else
                    {
                        response.onResponse(communities);
                    }
                }
            }.execute();
        }

    }

    public void getProvinces(final Response.Listener<Province[]> response, final Response.Listener<String> errorResponse, final int communityID)
    {
        new AsyncTask<Void, Void, Province[]>() {
            @Override
            protected Province[] doInBackground(Void... voids) {
                return dao.loadAllProvincesOfCommunity(communityID);
            }

            @Override
            protected void onPostExecute(Province[] provinces) {
                super.onPostExecute(provinces);
                if(provinces == null || provinces.length == 0)
                {
                    errorResponse.onResponse(databaseError);
                }
                else
                {
                    response.onResponse(provinces);
                }
            }
        }.execute();
    }

    public void getTowns(final Response.Listener<Town[]> response, final Response.Listener<String> errorResponse, final int provinceID)
    {
        new AsyncTask<Void, Void, Town[]>() {
            @Override
            protected Town[] doInBackground(Void... voids) {
                return dao.loadAllTownsOfProvince(provinceID);
            }

            @Override
            protected void onPostExecute(Town[] towns) {
                super.onPostExecute(towns);
                if(towns == null || towns.length == 0)
                {
                    errorResponse.onResponse(databaseError);
                }
                else
                {
                    response.onResponse(towns);
                    townsList = towns;
                }
            }
        }.execute();
    }

    private void initializeDatabase(Response.Listener<Community[]> response, Response.Listener<String> errorResponse)
    {
        //Communities
        ArrayList<Community> communities = new ArrayList<>();

        InputStream stream = resources.openRawResource(R.raw.communities);
        Scanner scanner = new Scanner(stream);

        String line;
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            communities.add(new Community(line.split("#")));
        }
        scanner.close();

        //Provinces
        ArrayList<Province> provinces = new ArrayList<>();

        stream = resources.openRawResource(R.raw.provinces);
        scanner = new Scanner(stream);

        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            provinces.add(new Province(line.split("#")));
        }
        scanner.close();

        //Towns
        ArrayList<Town> towns = new ArrayList<>();

        stream = resources.openRawResource(R.raw.towns);
        scanner = new Scanner(stream);

        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            towns.add(new Town(line.split("#")));
        }
        scanner.close();

        Community[] communitiesArray = new Community[communities.size()];
        Province[] provincesArray = new Province[provinces.size()];
        Town[] townsArray = new Town[towns.size()];

        communitiesArray = communities.toArray(communitiesArray);
        provincesArray = provinces.toArray(provincesArray);
        townsArray = towns.toArray(townsArray);

        insertLists(communitiesArray, provincesArray, townsArray, response, errorResponse);
    }

    private void insertLists(final Community[] communities, final Province[] provinces, final Town[] towns, final Response.Listener<Community[]> response, final Response.Listener<String> errorResponse)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insertCommunities(communities);
                dao.insertProvinces(provinces);
                dao.insertTowns(towns);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getCommunities(response, errorResponse, false);
            }
        }.execute();
    }

    public boolean verifyTextChanged(String s) {
        if(townsList != null) {
            for (int i = 0; i < townsList.length; i++) {
                if (s.equals(townsList[i].name)) {
                    selectedTown = townsList[i];
                    return true;
                }
            }
        }
        return false;
    }

    public Town getSelectedTown() {
        return selectedTown;
    }

    public void setTown(Town town) {
        selectedTown = town;
    }

    public void setGasPrice(GasType gas) {
        selectedGas = gas;
    }

    public void getPriceList(final Response.Listener<ArrayList<StationPrice>> response) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                gasQueries.addRequest(selectedTown.id, selectedGas.code,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response2) {
                                response.onResponse(gasQueries.parseJSONObject(response2));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                return null;
            }
        }.execute();
    }


}
