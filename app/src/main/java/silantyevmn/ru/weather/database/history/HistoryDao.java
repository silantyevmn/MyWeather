package silantyevmn.ru.weather.database.history;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import silantyevmn.ru.weather.database.city.CityEntity;

/**
 * Created by silan on 04.07.2018.
 */
@Dao
public interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    List<HistoryEntity> getAll();

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    HistoryEntity getCityHistoryByName(String name);

    @Insert
    void insert(HistoryEntity entity);

    @Insert
    void insertAll(List<HistoryEntity> entities);

    @Update
    void update(HistoryEntity entity);

    @Delete
    void delete(HistoryEntity entity);
}

