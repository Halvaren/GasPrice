package alvaro.sabi.rosquilletas.gasprice.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.android.volley.Response;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.gasSelection.GasSelectionPresenter;
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

    private Community[] communitiesList;
    private Province[] provincesList;
    private Town[] townsList;

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

    public void getCommunities(final Response.Listener<Community[]> response, boolean checkDatabaseEmpty)
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
                        initializeDatabase(response);
                    }
                    else
                    {
                        getCommunities(response, false);
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
                    response.onResponse(communities);
                    communitiesList = communities;
                }
            }.execute();
        }

    }

    public void getProvinces(final Response.Listener<Province[]> response, final int communityID)
    {
        new AsyncTask<Void, Void, Province[]>() {
            @Override
            protected Province[] doInBackground(Void... voids) {
                return dao.loadAllProvincesOfCommunity(communityID);
            }

            @Override
            protected void onPostExecute(Province[] provinces) {
                super.onPostExecute(provinces);
                response.onResponse(provinces);
                provincesList = provinces;
            }
        }.execute();
    }

    public void getTowns(final Response.Listener<Town[]> response, final int provinceID)
    {
        new AsyncTask<Void, Void, Town[]>() {
            @Override
            protected Town[] doInBackground(Void... voids) {
                return dao.loadAllTownsOfProvince(provinceID);
            }

            @Override
            protected void onPostExecute(Town[] towns) {
                super.onPostExecute(towns);
                response.onResponse(towns);
                townsList = towns;
            }
        }.execute();
    }

    private void initializeDatabase(Response.Listener<Community[]> response)
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

        insertLists(communitiesArray, provincesArray, townsArray, response);
    }

    private void insertLists(final Community[] communities, final Province[] provinces, final Town[] towns, final Response.Listener<Community[]> response)
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
                getCommunities(response, false);
            }
        }.execute();
    }
}
