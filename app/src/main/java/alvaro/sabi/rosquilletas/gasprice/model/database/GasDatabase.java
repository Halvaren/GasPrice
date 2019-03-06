package alvaro.sabi.rosquilletas.gasprice.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Community.class, Province.class, Town.class}, version = 1)
public abstract class GasDatabase extends RoomDatabase {
    public abstract GasDao gasDao();
}

