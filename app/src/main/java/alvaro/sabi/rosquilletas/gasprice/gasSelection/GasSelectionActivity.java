package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import alvaro.sabi.rosquilletas.gasprice.model.database.Community;
import alvaro.sabi.rosquilletas.gasprice.model.database.Province;
import alvaro.sabi.rosquilletas.gasprice.model.database.Town;
import alvaro.sabi.rosquilletas.gasprice.showPrices.ShowGasStationActivity;
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

                setTownText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTowns((Province) provinceSpinner.getAdapter().getItem(position));

                setTownText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        townText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onTownTextChanged(s.toString());
            }
        });

        enableShowPricesButton(false);

        presenter = new GasSelectionPresenter(this, this);

        showFuelTypes();
        getCommunities();
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
        Log.d("hola", "hola");
        ArrayAdapter<Town> adapter = new ArrayAdapter<Town>(this, android.R.layout.simple_dropdown_item_1line, towns);
        townText.setAdapter(adapter);

        onTownTextChanged(townText.getText().toString());
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

    private void onTownTextChanged(String s)
    {
        presenter.onTownTextChanged(s);
    }

    public void changedTownTextColor(boolean correct)
    {
        if(correct)
        {
            townText.setTextColor(Color.BLACK);
        }
        else
        {
            townText.setTextColor(Color.RED);
        }
    }

    public void enableShowPricesButton(boolean param)
    {
        showPricesButton.setEnabled(param);
    }

    private void setTownText(String value)
    {
        townText.setText(value);
        onTownTextChanged(townText.getText().toString());
    }

    public void nextActivity()
    {
        Intent intent = new Intent(GasSelectionActivity.this, ShowGasStationActivity.class);
        //intent.putExtra("Town", townText);
    }
}
