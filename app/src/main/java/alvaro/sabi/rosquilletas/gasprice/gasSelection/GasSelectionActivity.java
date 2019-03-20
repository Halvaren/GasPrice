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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

    //
    // Elementos del view
    //

    private Spinner communitySpinner; //Spinner que muestra las comunidades
    private Spinner provinceSpinner; //Spinner que muestra las provincias de la comunidad seleccionada
    private AutoCompleteTextView townText; //Las opciones de autocompletado son los pueblos de la provincia seleccionada
    private Spinner fuelTypeSpinner; //Spinner que muestra los tipos de combustibles
    private Button showPricesButton; //Botón para pasar a la siguiente actividad
    private ProgressBar progressBar; //Barra de progreso radial que aparece mientras se rellenan los spinners (se hace consulta a la base de datos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_selection_layout);

        communitySpinner = findViewById(R.id.communitySpinner);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        townText = findViewById(R.id.townEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);

        showPricesButton = findViewById(R.id.showPricesButton);

        progressBar = findViewById(R.id.progressBar);

        communitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Al seleccionar una comunidad, se piden las provincias de esa comunidad
                getProvinces((Community) communitySpinner.getAdapter().getItem(position));
                //Y se vacía el texto del pueblo
                setTownText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Al seleccionar una provincia, se piden los pueblos de esa provincia
                getTowns((Province) provinceSpinner.getAdapter().getItem(position));
                //Y se vacía el texto del pueblo
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
                //Después de hacer un cambio en el texto, se llama a este método enviando el texto actual, que avisará al presenter de ese cambio
                onTownTextChanged(s.toString());
            }
        });

        //El botón de cambio de actividad estará inicialmente desactivado, y solo se activará si el pueblo introducido en el texto existe
        enableShowPricesButton(false);

        presenter = new GasSelectionPresenter(this, this);

        showFuelTypes(); //Se rellena el spinner de combustibles (están guardados en memoria)
        getCommunities(); //Se piden al presenter las comunidades (están guardadas en la database)
    }

    //
    // Métodos para rellenar los spinner y el texto de autocompletado
    //

    //Pide al presenter la lista de comunidades
    public void getCommunities()
    {
        presenter.getCommunities();
    }

    //Pide al presenter la lista de provincias de la comunidad seleccionada
    public void getProvinces(Community community)
    {
        presenter.getProvinces(community.id);
    }

    //Pide al presenter la lista de pueblos de la provincia seleccionada
    public void getTowns(Province province)
    {
        presenter.getTowns(province.id);
    }

    //Este método será llamado por el presenter una vez tenga la lista de comunidades
    public void showCommunities(Community[] communities)
    {
        ArrayAdapter<Community> adapter = new ArrayAdapter<Community>(this, android.R.layout.simple_spinner_item, communities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(adapter); //Se le asigna al spinner un adapter que contiene la lista de comunidades

        //Siempre que se actualiza el adapter del spinner, se interpreta que se selecciona un elemento nuevo, por lo que se llama al método onItemSelected,
        //Por lo que pide a su vez la lista de provincias, y a su vez, la de pueblos
    }

    //Este método será llamado por el presenter una vez tenga la lista de provincias
    public void showProvinces(Province[] provinces)
    {
        ArrayAdapter<Province> adapter = new ArrayAdapter<Province>(this, android.R.layout.simple_spinner_item, provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter); //Se le asigna al spinner un adapter que contiene la lista de provincias
    }

    //Este método será llamados por el presenter una vez tenga la lista de pueblos
    public void showTowns(Town[] towns)
    {
        ArrayAdapter<Town> adapter = new ArrayAdapter<Town>(this, android.R.layout.simple_dropdown_item_1line, towns);
        townText.setAdapter(adapter); //Se le asigna al texto de autocompletado un adapter que contiene la lista de pueblos
    }

    //Rellana el spinner de los combustibles con los combustibles guardados en memoria
    private void showFuelTypes()
    {
        ArrayList<GasType> list = new ArrayList(); //Se genera una lista con los tipos de combustibles
        list.add(GasType.G95);
        list.add(GasType.G98);
        list.add(GasType.GOA);
        list.add(GasType.LPG);
        list.add(GasType.NGO);

        ArrayAdapter<GasType> adapter = new ArrayAdapter<GasType>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeSpinner.setAdapter(adapter); //Se le asigna al spinner un adapter que contiene la lista de combustibles
    }

    //
    // Métodos relacionados con el campo de pueblos
    //

    //Cambia el texto del campo del pueblo
    private void setTownText(String value)
    {
        townText.setText(value);
        onTownTextChanged(townText.getText().toString()); //En todas las llamadas a este método, el valor pasado es una cadena vacía, por lo que esta sentencia no tiene utilidad
        //Pero ello permite que si se pasara un valor distinto a una cadena vacía, comprobaría si el nuevo pueblo es correcto o no
    }

    //Envia al presenter el string con el texto actual del campo del pueblo para que compruebe si el pueblo existe en la provincia seleccionada
    private void onTownTextChanged(String s)
    {
        presenter.onTownTextChanged(s);
    }

    //Este método será llamado por el presenter una vez haya comprobado si el pueblo existe en la provincia seleccionada
    public void changedTownTextColor(boolean correct)
    {
        if(correct)
        {
            townText.setTextColor(Color.BLACK); //Si el pueblo es correcto, el texto del pueblo será de color negro
        }
        else
        {
            townText.setTextColor(Color.RED); //Si el pueblo es incorrecto, el texto del pueblo será de color rojo
        }
    }

    //
    // Otros métodos
    //

    //Activa/desactiva el botón para pasar a la siguiente actividad en función de si el pueblo introducido existe
    public void enableShowPricesButton(boolean param)
    {
        showPricesButton.setEnabled(param);
    }

    //Sirve mostrar mensajes de error (en concreto, errores de la database)
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Muestra y desmuestra la barra de progreso
    public void showProgressBar(boolean param)
    {
        progressBar.setVisibility(param ? View.VISIBLE : View.GONE);
    }

    //Será llamado cuando se pulse el botón para pasar a la siguiente actividad
    public void nextActivity(View view)
    {
        Intent intent = new Intent(GasSelectionActivity.this, ShowGasStationActivity.class);
        intent.putExtra("Town", presenter.getSelectedTown()); //A la siguiente actividad es necesario para el pueblo seleccionado (se lo pide al presenter, que se lo pedirá al model)
        intent.putExtra("GasType", ((GasType)fuelTypeSpinner.getSelectedItem())); //Y el combustible seleccionado

        startActivity(intent);
    }
}
