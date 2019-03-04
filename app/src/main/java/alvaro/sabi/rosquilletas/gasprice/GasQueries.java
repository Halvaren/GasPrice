package alvaro.sabi.rosquilletas.gasprice;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GasQueries {
    //Inicializa la RequestQueue
    public RequestQueue queue;

    public GasQueries(Context param){
        queue = Volley.newRequestQueue(param);
        String url = "http://www.google.com";

    }



}
