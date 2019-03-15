package alvaro.sabi.rosquilletas.gasprice.model.database;

import java.net.CookieHandler;
import java.util.ArrayList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GasDao {
    @Insert
    void insertCommunities(Community... communities);

    @Insert
    void insertProvinces(Province... provinces);

    @Insert
    void insertTowns(Town... towns);

    @Query("SELECT * FROM communities ORDER BY name")
    Community[] loadAllCommunities();

    @Query("SELECT * FROM provinces WHERE community_id = :community ORDER BY name")
    Province[] loadAllProvincesOfCommunity(int community);

    @Query("SELECT * FROM towns WHERE province_id = :province ORDER BY name")
    Town[] loadAllTownsOfProvince(int province);

    @Query("SELECT count(*) FROM communities")
    int numberOfRows();
}
