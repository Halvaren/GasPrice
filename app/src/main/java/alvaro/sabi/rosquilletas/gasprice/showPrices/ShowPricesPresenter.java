package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.Model;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;

public class ShowPricesPresenter {

    private ShowGasStationActivity view;
    private Model model;

    //Constructor
    public ShowPricesPresenter(ShowGasStationActivity view, Context context) {
        this.view = view;
        model = Model.getInstance(context);
    }

    //Método que solicitia al modelo la lista de precios. Para ello define un listener normal que llamará al método showPriceList una vez concluida la consulta y obtenida la lista
    //Y un listener de error que llamará al método showToast para mostrar un mensaje de error
    //Además, activa la visibilidad de la barra de progreso mientras se produce esa consulta
    public void getPriceList(Town selectedTown, GasType gasType)
    {

        view.showProgressBar(true);
        model.getPriceList(selectedTown, gasType,
        new Response.Listener<ArrayList<StationPrice>>()
        {
            public void onResponse(ArrayList<StationPrice> priceList) {
                showPriceList(priceList);
            }
        },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Network access error");
            }
        });
    }

    //Método que enviará la lista de precios a la view. También desactiva la visibilidad de la barra de progreso porque ya ha concluido la consulta de información.
    public void showPriceList(ArrayList<StationPrice> prices)
    {
        view.showProgressBar(false);
        view.showPriceList(prices);
    }

    //Método que pide a la view que muestre un Toast con un mensaje pasado por parámetro
    public void showToast(String message)
    {
        view.showToast(message);
    }
}


