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

        presenter = new GasSelectionPresenter(this, this);

        communitySpinner = findViewById(R.id.communitySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        townText = findViewById(R.id.townEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);

        showPricesButton = findViewById(R.id.showPricesButton);

        showFuelTypes();
    }

    public void showCommunities(Community[] communities)
    {
        ArrayAdapter<Community> adapter = new ArrayAdapter<Community>(this, android.R.layout.simple_spinner_item, communities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(adapter);
    }

    public void showProvinces(Province[] provinces)
    {
        ArrayAdapter<Province> adapter = new ArrayAdapter<Province>(this, android.R.layout.simple_spinner_item, provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter);
    }

    public void showTowns(Town[] towns)
    {
        ArrayAdapter<Town> adapter = new ArrayAdapter<Town>(this, android.R.layout.simple_dropdown_item_1line, towns);
        townText.setAdapter(adapter);
    }

    private void showFuelTypes()
    {
        ArrayList<GasType> list = new ArrayList<GasType>();
        list.add(GasType.G95);
        list.add(GasType.G98);
        list.add(GasType.GOA);
        list.add(GasType.LPG);
        list.add(GasType.NGO);

        ArrayAdapter<GasType> adapter = new ArrayAdapter<GasType>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeSpinner.setAdapter(adapter);
    }
}
