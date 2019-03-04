package alvaro.sabi.rosquilletas.gasprice.gasSelection;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import alvaro.sabi.rosquilletas.gasprice.R;
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
}
