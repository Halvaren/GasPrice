package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;
import androidx.appcompat.app.AppCompatActivity;

public class ShowGasStationActivity extends AppCompatActivity {

    private ListView gasStationList;
    private ListViewAdapter adapter;
    public ShowPricesPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gas_station_layout);

        gasStationList = findViewById(R.id.gasStationList);
        adapter = new ListViewAdapter(this, this);
        gasStationList.setAdapter(adapter);
        gasStationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(view, (StationPrice) gasStationList.getAdapter().getItem(position));

            }
        });

        presenter = new ShowPricesPresenter(this, this);

        Intent intent = getIntent();
        presenter.setTown((Town)intent.getParcelableExtra("Town"));
        presenter.setPrice((GasType) intent.getSerializableExtra("GasType"));


        getPriceList();
    }

    public void getPriceList() {
        presenter.getPriceList();
    }

    public void showPriceList(ArrayList<StationPrice> prices) {
        ((ListViewAdapter) gasStationList.getAdapter()).setStationGasList(prices);

    }

    public void showDialog(View view, StationPrice station) {
        ShowGasStationDialog dialog = new ShowGasStationDialog();
        dialog.setStation(station);
        dialog.show(getSupportFragmentManager(), "my_dialog");
    }
}
