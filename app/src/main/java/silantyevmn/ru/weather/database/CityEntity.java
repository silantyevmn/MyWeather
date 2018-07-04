package silantyevmn.ru.weather.database;

import android.arch.persistence.room.*;

import org.jetbrains.annotations.NotNull;

/**
 * Created by silan on 04.07.2018.
 */

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class CityEntity {

    @PrimaryKey
    @NotNull
    @ColumnInfo( name = "name")
    private String name;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public CityEntity(@NotNull String name) {
        this.name = name;
    }
}
