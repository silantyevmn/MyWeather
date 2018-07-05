package silantyevmn.ru.weather.database.history;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * Created by silan on 04.07.2018.
 */

@Entity
public class HistoryEntity {

    @NotNull
    @ColumnInfo( name = "name")
    private String name;

    @ColumnInfo
    private String operation;

    @PrimaryKey
    @ColumnInfo
    private long timeLog;

    public HistoryEntity(@NotNull String name, String operation, long timeLog) {
        this.name = name;
        this.operation = operation;
        this.timeLog = timeLog;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public long getTimeLog() {
        return timeLog;
    }
}
