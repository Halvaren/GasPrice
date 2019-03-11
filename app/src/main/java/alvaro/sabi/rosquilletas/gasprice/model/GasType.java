package alvaro.sabi.rosquilletas.gasprice.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum GasType /*implements Parcelable */{
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

    /*GasType(Parcel in) {
        gasName = in.readString();
        code = in.readInt();
    }

    public static final Creator<GasType> CREATOR = new Creator<GasType>() {
        @Override
        public GasType createFromParcel(Parcel in) {
            return new GasType(in);
        }

        @Override
        public GasType[] newArray(int size) {
            return new GasType[size];
        }
    };

    public String toString() {
        return gasName;
    }

    public int getCode(){
        return code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gasName);
        dest.writeInt(code);
    }*/
}
