package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;
import androidx.fragment.app.DialogFragment;

public class ShowGasStationDialog extends DialogFragment {

    private StationPrice station;
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_station_detail_dialog, null);



        builder.setView(view);

        TextView text = ((TextView) view.findViewById(R.id.stationAdress));
        text.setText(station.getAddress());
        ((TextView) view.findViewById(R.id.stationPrice)).setText(station.getProductPrice());

        builder.setTitle(station.getLabel());
        builder.setPositiveButton("Maps", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // mostrar el maps
                //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + station.getLatitude() + "," + station.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
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

    public void setContext(Context ctx) {
        context = ctx;
    }

    public void setStation(StationPrice station) {
        this.station = station;

    }

}
