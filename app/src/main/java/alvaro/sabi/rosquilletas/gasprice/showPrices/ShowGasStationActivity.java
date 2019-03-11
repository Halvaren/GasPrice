package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.os.Bundle;
import android.widget.ListView;

import alvaro.sabi.rosquilletas.gasprice.R;
import androidx.appcompat.app.AppCompatActivity;

public class ShowGasStationActivity extends AppCompatActivity {

    private ListView gasStationList;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gas_station_layout);

        gasStationList = findViewById(R.id.gasStationList);
        adapter = new ListViewAdapter(this);
        gasStationList.setAdapter(adapter);
    }

}
