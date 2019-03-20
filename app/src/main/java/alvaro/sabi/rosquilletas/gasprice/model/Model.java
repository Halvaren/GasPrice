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

    private static Model instance; //Instancia estática que hace al modelo singleton
    private final Resources resources; //Será util para acceder a los txt que contienen las comunidades, provincias y pueblos en caso de que la database esté vacía

    private GasQueries gasQueries; //Servirá para acceder a la red y hacer las consultas sobre las gasolineras

    private GasDatabase database; //La base de datos
    private GasDao dao; //La clase que contiene las sentencias para interactuar con la base de datos

    private Town[] townsList; //En esta lista se guardarán los pueblos de la provincia seleccionada. Es interesante guardarla en memoria para poder saber si el pueblo introducido en el campo de pueblo es correcto

    private Town selectedTown; //En esta variable se guardará el pueblo introducido en el campo pueblo de la primera actividad, siempre y cuando sea correcto. Ello facilita obtener el pueblo seleccionado cuando se haga el paso a la segunda actividad

    private String databaseError = "Error in the access to the database"; //String del error que se manda a la view para mostrarlo con un toast cuando la database no devuelve nada o devuelve una lista vacía

    //Este constructor es privado ya que sigue el modelo singleton
    private Model(Context context){
        resources = context.getResources();
        database = Room.databaseBuilder(context, GasDatabase.class, "database-name").build();
        dao = database.gasDao();

        gasQueries = new GasQueries(context);
    }

    //Método estático para poder acceder al modelo desde cualquier parte del código
    public static Model getInstance(Context context)
    {
        if(instance == null) //La primera vez que se llame a este método, se generará el modelo
        {
            instance = new Model(context);
        }
        return instance;
    }

    //Método que inicia la consulta a la base de datos para obtener la lista de comunidades. El último parámetro indica si se debe comprobar si la database está vacía o no
    public void getCommunities(final Response.Listener<Community[]> response, final Response.Listener<String> errorResponse, boolean checkDatabaseEmpty)
    {
        if(checkDatabaseEmpty) //Si el último parámetro de la llamada es true, se realizará una AsyncTask donde se pide a la base de datos el número de filas de la tabla de comunidades
        {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    return dao.numberOfRows();
                }

                @Override
                protected void onPostExecute(Integer rows) {
                    super.onPostExecute(rows);
                    if(rows == 0) //Si el número de filas es 0, significa que la database está vacía y hay que rellenarla, por lo que se llama al método de inicializar
                                 // (se le pasan los dos listeners recibidos por parámetro para que una vez inicializada la base de datos, se vuelva a llamar al método getCommunities, sabiendo que la database ya está inicializada)
                    {
                        initializeDatabase(response, errorResponse);
                    }
                    else //Si el número de filas no es 0, significa que la database no está vacía, por lo que se realiza una llamada recursiva al mismo método pero con el último parámetro en false, es decir, no se hará la comprobación de si la database está vacía o no
                    {
                        getCommunities(response, errorResponse, false);
                    }
                }
            }.execute();
        }
        else //Si el último parámetro de la llamada es false, se realizará una AsyncTask donde se pide a la base de datos la lista de comunidades
        {
            new AsyncTask<Void, Void, Community[]>() {
                @Override
                protected Community[] doInBackground(Void... voids) {
                    return dao.loadAllCommunities();
                }

                @Override
                protected void onPostExecute(Community[] communities) {
                    super.onPostExecute(communities);
                    if(communities == null || communities.length == 0) //Si la lista devuelta es nula o está vacía, se considera como un error de la database y se le comunica al listener de error
                    {
                        errorResponse.onResponse(databaseError);
                    }
                    else //En caso contrario, el listener normal recibirá la lista de comunidades, el cual se encargará de que el presenter se lo mande a la view y esta la muestre
                    {
                        response.onResponse(communities);
                    }
                }
            }.execute();
        }

    }

    //Método que inicia la consulta a la base de datos para obtener la lista de provincias de la comunidad seleccionada
    public void getProvinces(final Response.Listener<Province[]> response, final Response.Listener<String> errorResponse, final int communityID)
    {
        new AsyncTask<Void, Void, Province[]>() { //La AsyncTask pide a la base de datos que le devuelve la lista de provincias pasándole el id de la comunidad seleccionada
            @Override
            protected Province[] doInBackground(Void... voids) {
                return dao.loadAllProvincesOfCommunity(communityID);
            }

            @Override
            protected void onPostExecute(Province[] provinces) {
                super.onPostExecute(provinces);
                if(provinces == null || provinces.length == 0) //Si la lista devuelta es nula o está vacía, se considera como un error de la database y se le comunica al listener de error
                {
                    errorResponse.onResponse(databaseError);
                }
                else //En caso contrario, el listener normal recibirá la lista de provincias, el cual se encargará de que el presenter se lo mande a la view y esta la muestre
                {
                    response.onResponse(provinces);
                }
            }
        }.execute();
    }

    //Método que inicia la consulta a la base de datos para obtener la lista de pueblos de la provincia seleccionada
    public void getTowns(final Response.Listener<Town[]> response, final Response.Listener<String> errorResponse, final int provinceID)
    {
        new AsyncTask<Void, Void, Town[]>() { //La AsyncTask pide a la base de datos que le devuelve la lista de pueblos pasándole el id de la provincia seleccionada
            @Override
            protected Town[] doInBackground(Void... voids) {
                return dao.loadAllTownsOfProvince(provinceID);
            }

            @Override
            protected void onPostExecute(Town[] towns) {
                super.onPostExecute(towns);
                if(towns == null || towns.length == 0) //Si la lista devuelta es nula o está vacía, se considera como un error de la database y se le comunica al listener de error
                {
                    errorResponse.onResponse(databaseError);
                }
                else //En caso contrario, el listener normal recibirá la lista de pueblos, el cual se encargará de que el presenter se lo mande a la view y esta la muestre
                {
                    response.onResponse(towns);
                    townsList = towns;
                }
            }
        }.execute();
    }

    //Método que obtiene de los recursos las listas originales de comunidades, provincias y pueblos, para después insertarlas en la base de datos
    private void initializeDatabase(Response.Listener<Community[]> response, Response.Listener<String> errorResponse)
    {
        //Se utilizan listas ya que no conocemos con anterioridad el número de elementos
        //Pero posteriormente, esas listas se convierten en arrays antes de su inserción en la base de datos, y en el resto de operaciones (consulta de la base de datos, mostración de las listas, etc.) se utilizarán arrays

        //Comunidades
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

        //Provincias
        ArrayList<Province> provinces = new ArrayList<>();

        stream = resources.openRawResource(R.raw.provinces);
        scanner = new Scanner(stream);

        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            provinces.add(new Province(line.split("#")));
        }
        scanner.close();

        //Pueblos
        ArrayList<Town> towns = new ArrayList<>();

        stream = resources.openRawResource(R.raw.towns);
        scanner = new Scanner(stream);

        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            towns.add(new Town(line.split("#")));
        }
        scanner.close();

        //Aquí se realiza la conversión de listas a arrays
        Community[] communitiesArray = new Community[communities.size()];
        Province[] provincesArray = new Province[provinces.size()];
        Town[] townsArray = new Town[towns.size()];

        communitiesArray = communities.toArray(communitiesArray);
        provincesArray = provinces.toArray(provincesArray);
        townsArray = towns.toArray(townsArray);

        insertLists(communitiesArray, provincesArray, townsArray, response, errorResponse); //En este método se insertarán las arrays en la base de datos
    }

    //Método que dadas unas arrays, las inserta en la base de datos.
    //Tiene dos listeners como parámetro para posteriormente a las inserciones, se realiza una llamada a getCommunities con esos dos listener como parámetros
    private void insertLists(final Community[] communities, final Province[] provinces, final Town[] towns, final Response.Listener<Community[]> response, final Response.Listener<String> errorResponse)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) { //Se realizan las inserciones en segundo plano
                dao.insertCommunities(communities);
                dao.insertProvinces(provinces);
                dao.insertTowns(towns);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) { //Y una vez hechas, ya se puede realizar la consulta de comunidades con seguridad de que la database está llena
                super.onPostExecute(aVoid);
                getCommunities(response, errorResponse, false);
            }
        }.execute();
    }

    //Método que recibe una string correspondiente a un posible pueblo y que comprueba si está en la lista de pueblos de la provincia seleccionada
    public boolean verifyTextChanged(String s) {
        if(townsList != null) {
            for (int i = 0; i < townsList.length; i++) {
                if (s.equals(townsList[i].name)) {
                    selectedTown = townsList[i]; //En caso de que ese pueblo exista en esa provincia, se guardará en el atributo selectedTown para que sea sencillo poder pasar dicha información a la segunda actividad
                    return true; //También devuelve true para que con dicho resultado, la view haga las correspondientes modificaciones a nivel visual en función de él.
                }
            }
        }
        return false; //Devuelve false al acabar el bucle porque significa que no existe ningún pueblo con dicho nombre en la lista de pueblos de la provincia seleccionada
    }

    //Métodoq que devuelve el pueblo seleccionado. Será llamado cuando se vaya a hacer el paso a la segunda actividad
    public Town getSelectedTown() {
        return selectedTown;
    }

    //Método que solicita la lista de precios de las distintas gasolineras del pueblo seleccionado para el combustible seleccionado
    public void getPriceList(final Town selectedTown, final GasType selectedGas, final Response.Listener<ArrayList<StationPrice>> response, final Response.ErrorListener errorListener) {

        //Dado que el listener recibido necesita una lista de StationPrice y lo recibido por la request a la red es un JSON Object, es necesario realizar una conversión de la información
        //Además, dado que el resultado de la request es mandado directamente al listener, no es posible realizar la conversión de la información antes de ese envío, por lo que son necesarios dos listeners

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                gasQueries.addRequest(selectedTown.id, selectedGas.code,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject result) {
                                response.onResponse(gasQueries.parseJSONObject(result));
                            }
                        },
                        errorListener);
                return null;
            }
        }.execute();
    }

}
