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

    public GasSelectionPresenter(GasSelectionActivity view, Context context) //Constructor
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    //
    // Métodos para pedir y devolver las listas de comunidades, provincias y pueblos
    //

    public void getCommunities() //Se piden las comunidades
    {
        view.showProgressBar(true); //Se muestra la barra de progreso y se desmuestrará cuando se envíe la lista de comunidades
        model.getCommunities(new Response.Listener<Community[]>() {
                                 @Override
                                 public void onResponse(Community[] response) { //Listener que llamará al método que devuelve la lista de comunidades a la view cuando se haya completado la query a la database
                                     showCommunities(response);
                                 }
                             }, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) { //Listener que servirá para mostrar que se ha producido un error en la query a la database (no se ha recibido nada o se ha recibido una lista vacía)
                                     showToast(response);
                                 }
                             }
                , true);
    }

    public void getProvinces(int communityID) //Se piden las provincias de la comunidad seleccionada
    {
        view.showProgressBar(true); //Se muestra la barra de progreso y se desmuestrará cuando se envíe la lista de provincias
        model.getProvinces(new Response.Listener<Province[]>() {
                            @Override
                            public void onResponse(Province[] response) { //Listener que llamará al método que devuelve la lista de provincias a la view cuando se haya completado la query a la database
                                showProvinces(response);
                            }
                        }, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { //Listener que servirá para mostrar que se ha producido un error en la query a la database (no se ha recibido nada o se ha recibido una lista vacía)
                                showToast(response);
                            }
                        }, communityID);
    }

    public void getTowns(int provinceID) //Se piden los pueblos de las provincia seleccionada
    {
        view.showProgressBar(true); //Se muestra la barra de progreso y se desmuestrará cuando se envíe la lista de pueblos
        model.getTowns(new Response.Listener<Town[]>() {
                        @Override
                        public void onResponse(Town[] response) { //Listener que llamará al método que devuelve la lista de pueblos a la view cuando se haya completado la query a la database
                            showTowns(response);
                        }
                    }, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { //Listener que servirá para mostrar que se ha producido un error en la query a la database (no se ha recibido nada o se ha recibido una lista vacía)
                            showToast(response);
                        }
                    }, provinceID);
    }

    public void showCommunities(Community[] list) //Envia al view la lista de comunidades ya obtenida por el modelo
    {
        view.showProgressBar(false); //Hace desaparecer la barra de progreso porque ya se tiene la lista de comunidades
        view.showCommunities(list);
    }

    public void showProvinces(Province[] list) //Envia al view la lista de provincias ya obtenida por el modelo
    {
        view.showProgressBar(false); //Hace desaparecer la barra de progreso porque ya se tiene la lista de provincias
        view.showProvinces(list);
    }

    public void showTowns(Town[] list) //Envia al view la lista de pueblos ya obtenida por el modelo
    {
        view.showProgressBar(false); //Hace desaparecer la barra de progreso porque ya se tiene la lista de pueblos
        view.showTowns(list);
    }

    //
    // Otros métodos
    //

    public void onTownTextChanged(String s)  //Recibe un texto correspondiente a un posible pueblo. Le pide al modelo que verifique si existe y
                                             //manda al resultado a la view para cambiar el color del texto del pueblo y activar/desactivar el botón de cambiar de actividad
    {
        boolean correct = model.verifyTextChanged(s);
        view.changedTownTextColor(correct);
        view.enableShowPricesButton(correct);
    }

    public Town getSelectedTown() //Devuelve el pueblo seleccionado pidiéndoselo al modelo
    {
        return model.getSelectedTown();
    }

    public void showToast(String message) //Recibe un mensaje que mostrar mediante un Toast (el view generará el Toast)
    {
        view.showToast(message);
    }
}
