package com.example.pomos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pomos.database.dao.FunDao
import com.example.pomos.database.model.Tarefa

@Database(entities = [Tarefa :: class],
    version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun funDao() : FunDao
    companion object {
        fun instancia(context : Context) : AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "Pomos.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}