package alvaro.sabi.rosquilletas.gasprice.showPrices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.gasprice.R;
import alvaro.sabi.rosquilletas.gasprice.model.StationPrice;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StationPrice> stationPriceList;

    //Constructor
    public ListViewAdapter (Context param0)
    {
        context = param0;
        stationPriceList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return stationPriceList.size();
    }

    @Override
    public Object getItem(int position) {
        return stationPriceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StationPrice stationPrice = (StationPrice) getItem(position);

        //Cada elemento dispone de una layout específica donde se muestra el precio del combustible seleccionado y la dirección de la gasolinera
        convertView = LayoutInflater.from(context).inflate(R.layout.gas_station_list_item, null);
        TextView priceText = convertView.findViewById(R.id.priceText);
        TextView addressText = convertView.findViewById(R.id.addressText);

        priceText.setText(stationPrice.getProductPrice());
        addressText.setText(stationPrice.getAddress());

        return convertView;
    }

    //Método que sirve para actualizar la lista de StationPrice
    public void setStationGasList(ArrayList<StationPrice> list)
    {
        stationPriceList = list;

        notifyDataSetChanged();
    }


}
