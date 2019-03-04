package alvaro.sabi.rosquilletas.gasprice.model.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "communities")
public class Community {

    @PrimaryKey
    public int id;

    public String name;

    @Override
    public String toString()
    {
        return name;
    }
}
