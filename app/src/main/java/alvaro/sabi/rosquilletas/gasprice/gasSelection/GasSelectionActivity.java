package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.GasType;
import alvaro.sabi.rosquilletas.gasprice.model.SelectedLocation;
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

        communitySpinner = findViewById(R.id.communitySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        townText = findViewById(R.id.townEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);

        showPricesButton = findViewById(R.id.showPricesButton);

        communitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getProvinces((Community) communitySpinner.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTowns((Province) provinceSpinner.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        presenter = new GasSelectionPresenter(this, this);

        showFuelTypes();

        getCommunities();

        if(savedInstanceState != null)
        {
            presenter.setSelectedLocation((SelectedLocation) savedInstanceState.getParcelable("SelectedLocation"));
            presenter.setSelectedTown((Town) savedInstanceState.getParcelable("SelectedTown"));
            communitySpinner.setSelection(presenter.getSelectedCommunityID());
            provinceSpinner.setSelection(presenter.getSelectedProvinceID());
            if(presenter.getSelectedTown() != null)
                townText.setText(presenter.getSelectedTown().name);
            fuelTypeSpinner.setSelection(presenter.getSelectedGasTypeID());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("SelectedLocation", presenter.getSelectedLocation());
        savedInstanceState.putParcelable("SelectedTown", presenter.getSelectedTown());
    }

    public void getCommunities()
    {
        presenter.getCommunities();
    }

    public void getProvinces(Community community)
    {
        presenter.getProvinces(community.id);
    }

    public void getTowns(Province province)
    {
        presenter.getTowns(province.id);
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
