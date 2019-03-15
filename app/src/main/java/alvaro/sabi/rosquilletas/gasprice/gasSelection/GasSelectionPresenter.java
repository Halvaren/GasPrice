package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import alvaro.sabi.rosquilletas.gasprice.model.Model;
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;

public class GasSelectionPresenter {

    private GasSelectionActivity view;
    private Model model;

    public GasSelectionPresenter(GasSelectionActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void getCommunities()
    {
        model.getCommunities(new Response.Listener<Community[]>() {
                                 @Override
                                 public void onResponse(Community[] response) {
                                     showCommunities(response);
                                 }
                             }, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {
                                     showToast(response);
                                 }
                             }
                , true);
    }

    public void getProvinces(int communityID)
    {
        model.getProvinces(new Response.Listener<Province[]>() {
                            @Override
                            public void onResponse(Province[] response) {
                                showProvinces(response);
                            }
                        }, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                showToast(response);
                            }
                        }, communityID);
    }

    public void getTowns(int provinceID)
    {
        model.getTowns(new Response.Listener<Town[]>() {
                        @Override
                        public void onResponse(Town[] response) {
                            showTowns(response);
                        }
                    }, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showToast(response);
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

    public void onTownTextChanged(String s) {
        boolean correct = model.verifyTextChanged(s);
        view.changedTownTextColor(correct);
        view.enableShowPricesButton(correct);
    }

    public Town getSelectedTown() {
        return model.getSelectedTown();
    }

    public void showToast(String message)
    {
        view.showToast(message);
    }
}
