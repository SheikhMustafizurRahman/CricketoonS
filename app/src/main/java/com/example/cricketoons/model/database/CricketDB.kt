package com.example.cricketoons.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.model.fixtures.FixtureData

@Database(
    entities = [FixtureData::class],
    version = 2,
    exportSchema = false
)
abstract class CricketDB : RoomDatabase() {
    abstract fun cricketDao(): CricDao

    companion object {
        @Volatile
        private var INSTANCE: CricketDB? = null

        fun getDatabase(context: Context): CricketDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CricketDB::class.java,
                    "cricketDB"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}