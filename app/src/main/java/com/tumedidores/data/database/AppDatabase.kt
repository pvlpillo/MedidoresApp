package com.tumedidores.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tumedidores.data.entity.Medicion
import com.tumedidores.data.repository.MedicionDao

@Database(
    entities = [Medicion::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medidores_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}