package alvaro.sabi.rosquilletas.gasprice.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GasQueries {
    private RequestQueue queue; //Cola de peticiones a la red
    private String url; //URL de la web donde se realizan las peticiones

    //Constructor
    public GasQueries(Context param){
        queue = Volley.newRequestQueue(param);
        url = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipioProducto/";

    }

    //Método para añadir una nueva request a la cola
    public void addRequest(int townId, int gasTypeId, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        //Se completa la URL con el id del pueblo y el id del combustible para realizar la consulta
        String finalUrl = url + Integer.toString(townId) + "/" + Integer.toString(gasTypeId);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null, listener, error);

        queue.add(request); //Una vez realizada la request, si todo ha ido bien, le enviará al response un JSONObject
    }

    //Método para convertir un JSONObject en una lista de StationPrice
    public ArrayList<StationPrice> parseJSONObject(JSONObject response) {
        ArrayList<StationPrice> list = new ArrayList();
        try {
            JSONArray JSONlist = response.getJSONArray("ListaEESSPrecio"); //Los campos necesarios están almacenadas en una JSONArray
            for (int i = 0; i < JSONlist.length(); i++) {

                JSONObject currentObject = (JSONObject) JSONlist.get(i);
                list.add(new StationPrice(currentObject.getString("Rótulo"),
                        currentObject.getString("Dirección"),
                        currentObject.getString("PrecioProducto"),
                        currentObject.getString("Latitud").replaceAll(",", "."),
                        //Para que la localización en Maps funcione correctamente, es necesario que la latitud y longitud utilicen punto y no coma
                        currentObject.getString("Longitud (WGS84)").replaceAll(",", ".")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }



}
