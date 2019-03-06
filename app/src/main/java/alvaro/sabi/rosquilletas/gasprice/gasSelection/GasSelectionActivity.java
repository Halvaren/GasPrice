package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;
import androidx.appcompat.app.AppCompatActivity;

public class GasSelectionActivity extends AppCompatActivity {

    private GasSelectionPresenter presenter;

    private Spinner communitySpinner;
    private Spinner provinceSpinner;
    private AutoCompleteTextView townText;
    private Spinner fuelTypeSpinner;

    private Button showPricesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_selection_layout);
        super.onCreate(savedInstanceState);

        presenter = new GasSelectionPresenter(this, this);

        communitySpinner = findViewById(R.id.communitySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        townText = findViewById(R.id.townEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);

        showPricesButton = findViewById(R.id.showPricesButton);
    }

    public void showCommunities(Community[] communities)
    {
        ArrayList<String> communityNames = new ArrayList<String>();
        for(int i = 0; i < communities.length; i++)
        {
            communityNames.add(communities[i].toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, communityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(adapter);
    }

    public void showProvinces(Province[] provinces)
    {
        ArrayList<String> provinceNames = new ArrayList<String>();
        for(int i = 0; i < provinces.length; i++)
        {
            provinceNames.add(provinces[i].toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter);
    }

    public void showTowns(Town[] towns)
    {
        ArrayList<String> townNames = new ArrayList<String>();
        for(int i = 0; i < towns.length; i++)
        {
            townNames.add(towns[i].toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, townNames);
        townText.setAdapter(adapter);
    }

    private void showFuelTypes()
    {
        
    }
}
