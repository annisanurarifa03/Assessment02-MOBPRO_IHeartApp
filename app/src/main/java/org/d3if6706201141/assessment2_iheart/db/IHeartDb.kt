package org.d3if6706201141.assessment2_iheart.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [IHeartEntity::class], version = 1, exportSchema = false)
abstract class IHeartDb : RoomDatabase() {
    abstract val dao: IHeartDao
    companion object {
        @Volatile
        private var INSTANCE: IHeartDb? = null
        fun getInstance(context: Context): IHeartDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        IHeartDb::class.java,
                        "detakJantung.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}