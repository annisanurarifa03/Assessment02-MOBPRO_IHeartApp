package org.d3if6706201141.assessment2_iheart.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IHeartDao {
    @Insert
    fun insert(detakJantung: IHeartEntity)

    @Query("SELECT * FROM detakJantung ORDER BY id DESC")
    fun getLastDetakJantung(): LiveData<List<IHeartEntity>>

    @Query("DELETE FROM detakJantung")
    fun clearData()
}
