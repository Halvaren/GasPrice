package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;

public interface ShowGasStationViewInterface {

    void getPriceList(Town selectedTown, GasType selectedGas);

    void showPriceList(ArrayList<StationPrice> prices);

    void showToast(String message);

    void showDialog(View view, StationPrice station);

    void showProgressBar(boolean param);
}
