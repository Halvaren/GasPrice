package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        adapter = new ListViewAdapter(this);
        gasStationList.setAdapter(adapter);

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
}
