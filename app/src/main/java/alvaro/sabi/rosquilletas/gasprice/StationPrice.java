package alvaro.sabi.rosquilletas.gasprice;

public class StationPrice { //hay que parcelarlo :)
    public String rotulo;
    public String direccion;
    public String precioProducto;
    public String latitud;
    public String longitud;

    public StationPrice(String rotulo, String direccion, String precioProducto, String latitud, String longitud){
        this.rotulo = rotulo;
        this.direccion = direccion;
        this.precioProducto = precioProducto;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
