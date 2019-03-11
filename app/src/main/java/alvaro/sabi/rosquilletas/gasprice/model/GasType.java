package alvaro.sabi.rosquilletas.gasprice.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum GasType {
    G98("Gasoline 98", 3),
    GOA("Diesel",4),
    NGO("Premium Diesel", 5),
    G95("Gasoline 95", 15),
    LPG("Liquefies petroleum gases", 17);

    public final String gasName;
    public final int code;

    GasType(String gasName, int code) {
        this.gasName = gasName;
        this.code = code;
    }
}
