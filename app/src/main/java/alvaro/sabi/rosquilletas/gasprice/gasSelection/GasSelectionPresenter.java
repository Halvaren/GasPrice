package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.content.Context;

import com.android.volley.Response;

import alvaro.sabi.rosquilletas.gasprice.model.Model;
import alvaro.sabi.rosquilletas.gasprice.model.SelectedLocation;
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

    public int getSelectedCommunityID()
    {
        return Model.getInstance(null).getSelectedCommunityID();
    }

    public int getSelectedProvinceID()
    {
        return Model.getInstance(null).getSelectedProvinceID();
    }

    public Town getSelectedTown()
    {
        return Model.getInstance(null).getSelectedTown();
    }

    public int getSelectedGasTypeID()
    {
        return Model.getInstance(null).getSelectedGasTypeID();
    }

    public void setSelectedCommunityID(int param)
    {
        Model.getInstance(null).setSelectedCommunityID(param);
    }

    public void setSelectedProvinceID(int param)
    {
        Model.getInstance(null).setSelectedProvinceID(param);
    }

    public void setSelectedTown(Town param)
    {
        Model.getInstance(null).setSelectedTown(param);
    }

    public void setSelectedGasTypeID(int param)
    {
        Model.getInstance(null).setSelectedGasTypeID(param);
    }

    public SelectedLocation getSelectedLocation()
    {
        return Model.getInstance(null).getSelectedLocation();
    }

    public void setSelectedLocation(SelectedLocation param)
    {
        Model.getInstance(null).setSelectedLocation(param);
    }
}
