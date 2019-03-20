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

    private StationPrice station; //Gasolinera de la cual se mostrará información
    private Context context; //El contexto es necesario para conocer si la aplicación Maps está instalada

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_station_detail_dialog, null); //Se le introduce el layout correspondiente

        builder.setView(view);

        ((TextView) view.findViewById(R.id.stationAdress)).setText(station.getAddress()); //Introduce en el text view la dirección de la gasolinera
        ((TextView) view.findViewById(R.id.stationPrice)).setText(station.getProductPrice()); //Introduce en el text view el precio del combustible seleccionado

        builder.setTitle(station.getLabel()); //El título del dialog es el rótulo de la gasolinera
        builder.setPositiveButton("Maps", new DialogInterface.OnClickListener() { //El botón positivo sirve para abrir el maps con la localización de la gasolinera
            public void onClick(DialogInterface dialog, int id) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + station.getLatitude() + "," + station.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    startActivity(mapIntent); //Inicia el maps siempre y cuando esté instalada en el teléfono
                }
            }


        });
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() { //El botón negativo para cerrar el dialog
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    //Método para determinar el contexto
    public void setContext(Context ctx) {
        context = ctx;
    }

    //Método para determinar la gasolinera seleccionada
    public void setStation(StationPrice station) {
        this.station = station;

    }

}
