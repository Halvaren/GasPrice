package alvaro.sabi.rosquilletas.gasprice.model;

import android.os.Parcel;
import android.os.Parcelable;

import alvaro.sabi.rosquilletas.gasprice.model.database.*;

public class SelectedLocation implements Parcelable {

    public final int selectedCommunityID;
    public final int selectedProvinceID;
    public final int selectedGasTypeID;

    protected SelectedLocation(Parcel in) {
        selectedCommunityID = in.readInt();
        selectedProvinceID = in.readInt();
        selectedGasTypeID = in.readInt();
    }

    public static final Creator<SelectedLocation> CREATOR = new Creator<SelectedLocation>() {
        @Override
        public SelectedLocation createFromParcel(Parcel in) {
            return new SelectedLocation(in);
        }

        @Override
        public SelectedLocation[] newArray(int size) {
            return new SelectedLocation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(selectedCommunityID);
        dest.writeInt(selectedProvinceID);
        dest.writeInt(selectedGasTypeID);
    }

    public SelectedLocation(int param0, int param1, int param2)
    {
        selectedCommunityID = param0;
        selectedProvinceID = param1;
        selectedGasTypeID = param2;
    }
}
