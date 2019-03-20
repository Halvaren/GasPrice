package alvaro.sabi.rosquilletas.gasprice.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "provinces")
public class Province {

    public Province() {}

    @PrimaryKey
    public int id;

    public String name;

    @ForeignKey(entity = Community.class, parentColumns = "id", childColumns = "community_id")
    @ColumnInfo(name = "community_id")
    public int communityID;

    public String toString()
    {
        return name;
    }

    public Province(String[] params)
    {
        id = Integer.parseInt(params[0]);
        name = params[1];
        communityID = Integer.parseInt(params[2]);
    }
}
