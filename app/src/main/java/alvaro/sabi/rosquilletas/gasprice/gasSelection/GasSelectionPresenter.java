package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.content.Context;

import alvaro.sabi.rosquilletas.gasprice.model.Model;

public class GasSelectionPresenter {

    private GasSelectionActivity view;
    private Model model;

    public GasSelectionPresenter(GasSelectionActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }
}
