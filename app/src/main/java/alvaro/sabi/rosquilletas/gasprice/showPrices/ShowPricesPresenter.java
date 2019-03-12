package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Context;

import com.android.volley.Response;

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

        model.getPriceList(new Response.Listener<ArrayList<StationPrice>>()
        {
            public void onResponse(ArrayList<StationPrice> priceList) {
                showPriceList(priceList);
            }
        });
    }

    public void showPriceList(ArrayList<StationPrice> prices) {
        view.showPriceList(prices);
    }


}


