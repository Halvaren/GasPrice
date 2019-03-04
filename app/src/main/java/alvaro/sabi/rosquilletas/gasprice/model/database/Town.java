package alvaro.sabi.rosquilletas.gasprice.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "towns")
public class Town implements Parcelable, Comparable<Town> {

    @PrimaryKey
    public int id;

    public String name;

    @ForeignKey(entity = Province.class, parentColumns = "id", childColumns = "province_id")
    @ColumnInfo(name = "province_id")
    public int provinceID;

    public String toString()
    {
        return name;
    }

    //
    //Parcelable things
    //

    protected Town(Parcel in) {
    }

    public static final Creator<Town> CREATOR = new Creator<Town>() {
        @Override
        public Town createFromParcel(Parcel in) {
            return new Town(in);
        }

        @Override
        public Town[] newArray(int size) {
            return new Town[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    //
    //Comparable things
    //

    @Override
    public int compareTo(Town other) {
        if(this.name.compareTo(other.name) > 0) return 1;
        else if(this.name.compareTo(other.name) < 0) return -1;
        return 0;
    }
}
