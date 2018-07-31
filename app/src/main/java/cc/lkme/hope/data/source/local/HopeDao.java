package cc.lkme.hope.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface HopeDao {

    /**
     * Update the complete status of a task
     *
     * @param taskId    id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE tasks SET completed = :completed WHERE entryid = :taskId")
    void updateCompleted(String taskId, boolean completed);


}
