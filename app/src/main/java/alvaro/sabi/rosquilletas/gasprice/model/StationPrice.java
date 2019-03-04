package alvaro.sabi.rosquilletas.gasprice.model;

public class StationPrice { //hay que parcelarlo :)
    private String label; //Rotulo, nombre de la gasolinera
    private String address; //Direccion de la gasolinera
    private String productPrice; //Precio del producto en cuestión en la gasolinera
    private String latitude; //Latitud geografica de la gasolinera
    private String longitude; //Longitud geografica de la gasolinera

    public StationPrice(String label, String address, String productPrice, String latitude, String longitude){
        this.label = label;
        this.address = address;
        this.productPrice = productPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLabel()
    {
        return label;
    }

    public String getAddress()
    {
        return address;
    }

    public String getProductPrice()
    {
        return productPrice;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }
}
