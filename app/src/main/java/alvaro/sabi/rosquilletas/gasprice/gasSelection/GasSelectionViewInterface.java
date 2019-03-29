package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.view.View;
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;

public interface GasSelectionViewInterface {

    void getCommunities();

    void getProvinces(Community community);

    void getTowns(Province province);

    void showCommunities(Community[] communities);

    void showProvinces(Province[] provinces);

    void showTowns(Town[] towns);

    void showFuelTypes();

    void setTownText(String value);

    void onTownTextChanged(String s);

    void changedTownTextColor(boolean correct);

    void enableShowPricesButton(boolean param);

    void showToast(String message);

    void showProgressBar(boolean param);

    void nextActivity(View view);
}
