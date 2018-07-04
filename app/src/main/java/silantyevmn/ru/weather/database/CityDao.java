package silantyevmn.ru.weather.database;

import android.arch.persistence.room.*;
import java.util.List;

/**
 * Created by silan on 04.07.2018.
 */
@Dao
public interface CityDao {
    @Query("SELECT * FROM CityEntity")
    List<CityEntity> getAll();

    @Query("SELECT * FROM CityEntity WHERE name LIKE :name")
    CityEntity getCityByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CityEntity entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CityEntity> entities);

    @Update
    void update(CityEntity entity);

    @Delete
    void delete(CityEntity entity);
}

