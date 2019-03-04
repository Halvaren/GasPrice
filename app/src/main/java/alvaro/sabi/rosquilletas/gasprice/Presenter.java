package alvaro.sabi.rosquilletas.gasprice;

import android.content.Context;

public class Presenter {
    private Model model;
    private GasSelectionActivity view;
    public Context context;

    public Presenter(GasSelectionActivity param) {
        view = param;
        model = new Model(this);
    }
}
