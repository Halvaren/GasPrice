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
    //Inicializa la RequestQueue
    private RequestQueue queue;
    private String url;

    public GasQueries(Context param){
        queue = Volley.newRequestQueue(param);
        url = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipioProducto/";

    }

    public void addRequest(int townId, int gasTypeId, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        String finalUrl = url + Integer.toString(townId) + "/" + Integer.toString(gasTypeId);

        Log.d("f", "f1");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null, listener, error);

        queue.add(request);
    }

    public ArrayList<StationPrice> parseJSONObject(JSONObject response) {
        ArrayList<StationPrice> list = new ArrayList();
        try {
            JSONArray JSONlist = response.getJSONArray("ListaEESSPrecio");
            for (int i = 0; i < JSONlist.length(); i++) {

                JSONObject currentObject = (JSONObject) JSONlist.get(i);
                list.add(new StationPrice(currentObject.getString("Rótulo"),
                        currentObject.getString("Dirección"),
                        currentObject.getString("PrecioProducto"),
                        currentObject.getString("Latitud").replaceAll(",", "."),
                        currentObject.getString("Longitud (WGS84)").replaceAll(",", ".")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }



}
