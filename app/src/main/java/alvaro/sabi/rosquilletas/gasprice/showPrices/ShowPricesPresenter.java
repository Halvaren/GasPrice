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

    public ShowPricesPresenter(ShowGasStationActivity view, Context context) {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void setTown(Town town) {
        model.setTown(town);
    }

    public void setPrice(GasType gas) {
        model.setGasPrice(gas);
    }

    public void getPriceList(){

        view.showProgressBar(true);
        model.getPriceList(new Response.Listener<ArrayList<StationPrice>>()
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

    public void showPriceList(ArrayList<StationPrice> prices) {
        view.showProgressBar(false);
        view.showPriceList(prices);
    }

    public void showToast(String message)
    {
        view.showToast(message);
    }
}


