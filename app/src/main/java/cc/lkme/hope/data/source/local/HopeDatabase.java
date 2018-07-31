package cc.lkme.hope.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cc.lkme.hope.data.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class HopeDatabase extends RoomDatabase {

    public abstract HopeDao hopeDao();

}
