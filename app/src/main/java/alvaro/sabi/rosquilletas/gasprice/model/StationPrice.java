package alvaro.sabi.rosquilletas.gasprice.model;

public class StationPrice { //hay que parcelarlo :)
    private String label; //Rotulo, nombre de la gasolinera
    private String address; //Direccion de la gasolinera
    private String productPrice; //Precio del producto en cuestión en la gasolinera
    private String latitude; //Latitud geografica de la gasolinera
    private String longitude; //Longitud geografica de la gasolinera

    //Constructor
    public StationPrice(String label, String address, String productPrice, String latitude, String longitude){
        this.label = label;
        this.address = address;
        this.productPrice = productPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Devuelve el rótulo
    public String getLabel()
    {
        return label;
    }

    //Devuelve el dirección
    public String getAddress()
    {
        return address;
    }

    //Devuelve el precio del producto
    public String getProductPrice()
    {
        return productPrice;
    }

    //Devuelve la latitud de la gasolinera
    public String getLatitude()
    {
        return latitude;
    }

    //Devuelve la longitud de la gasolinera
    public String getLongitude()
    {
        return longitude;
    }
}
