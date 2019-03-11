package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.content.Context;

import com.android.volley.Response;

import alvaro.sabi.rosquilletas.gasprice.model.Model;
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;

public class GasSelectionPresenter {

    private GasSelectionActivity view;

    public GasSelectionPresenter(GasSelectionActivity view, Context context)
    {
        this.view = view;
        Model.getInstance(context);
    }

    public void getCommunities()
    {
        Model.getInstance(null).getCommunities(new Response.Listener<Community[]>() {
            @Override
            public void onResponse(Community[] response) {
                showCommunities(response);
            }
        }, true);
    }

    public void getProvinces(int communityID)
    {
        Model.getInstance(null).getProvinces(new Response.Listener<Province[]>() {
            @Override
            public void onResponse(Province[] response) {
                showProvinces(response);
            }
        }, communityID);
    }

    public void getTowns(int provinceID)
    {
        Model.getInstance(null).getTowns(new Response.Listener<Town[]>() {
            @Override
            public void onResponse(Town[] response) {
                showTowns(response);
            }
        }, provinceID);
    }

    public void showCommunities(Community[] list)
    {
        view.showCommunities(list);
    }

    public void showProvinces(Province[] list)
    {
        view.showProvinces(list);
    }

    public void showTowns(Town[] list)
    {
        view.showTowns(list);
    }
}
