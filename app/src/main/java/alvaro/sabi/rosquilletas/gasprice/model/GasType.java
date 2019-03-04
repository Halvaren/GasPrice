package alvaro.sabi.rosquilletas.gasprice.model;

public enum GasType {
    G98("Gasoline 98", 3),
    GOA("Diesel",4),
    NGO("Premium Disel", 5),
    G95("Gasoline 95", 15),
    LPG("Liquefies petroleum gases", 17);

    private final String gasName;
    private final int code;

    GasType(String gasName, int code) {
        this.gasName = gasName;
        this.code = code;
    }
    private String gasName() {return gasName;}
    private int code(){return code;}

}
