package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import androidx.fragment.app.DialogFragment;

public class ShowGasStationDialog extends DialogFragment {

    private StationPrice station;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_station_detail_dialog, null);


        //((TextView) view.findViewById(R.id.stationPrice)).setText(station.getProductPrice());

        builder.setView(view);

        TextView text = ((TextView) view.findViewById(R.id.addressText));
        text.setText(station.getAddress());

        builder.setPositiveButton("Maps", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // mostrar el maps
            }
        });
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

                // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setStation(StationPrice station) {
        this.station = station;

    }

}
