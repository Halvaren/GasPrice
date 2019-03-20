package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;
import androidx.appcompat.app.AppCompatActivity;

public class ShowGasStationActivity extends AppCompatActivity {

    private ListView gasStationList; //Lista donde se mostrarán los precios del combustible seleccionado en el pueblo seleccionado
    private ProgressBar progressBar; //Barra de progreso que se mostrará mientras se estén cargando los elementos de la lista de precios

    private ShowPricesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gas_station_layout);

        gasStationList = findViewById(R.id.gasStationList);
        ListViewAdapter adapter = new ListViewAdapter(this); //Se necesita de una clase específica para el adapter ya que los elementos emplean un layout específico
        gasStationList.setAdapter(adapter);
        gasStationList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Listener que detecta el click sobre un elemento
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(view, (StationPrice) gasStationList.getAdapter().getItem(position)); //Al realizarse el click se mostrará un dialog con información de la gasolinera seleccionada

            }
        });

        progressBar = findViewById(R.id.progressBar2);

        presenter = new ShowPricesPresenter(this, this);

        //Se viene de la primera actividad, la cual ha enviado información sobre el pueblo seleccionado y el combustible seleccionado
        Intent intent = getIntent();
        getPriceList((Town)intent.getParcelableExtra("Town"), (GasType) intent.getSerializableExtra("GasType"));
    }

    //Método que solicita al presenter la lista de precios
    public void getPriceList(Town selectedTown, GasType selectedGas) {
        presenter.getPriceList(selectedTown, selectedGas);
    }

    //Método que recibe la lista de precios solicitada y la muestra actualizando el adapter de la list view
    public void showPriceList(ArrayList<StationPrice> prices) {
        ((ListViewAdapter) gasStationList.getAdapter()).setStationGasList(prices);

    }

    //Método utilizado para mostrar mensajes de error, en concreto, los posibles mensajes de error al intentar acceder a la red
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Método que muestra el dialog con la información de la gasolinera seleccionada
    public void showDialog(View view, StationPrice station) {

        ShowGasStationDialog dialog = new ShowGasStationDialog();
        dialog.setStation(station);
        dialog.setContext(this);
        dialog.show(getSupportFragmentManager(), "my_dialog");
    }

    //Método para mostrar u ocultar la barra de progreso
    public void showProgressBar(boolean param)
    {
        progressBar.setVisibility(param ? View.VISIBLE : View.GONE);
    }
}
