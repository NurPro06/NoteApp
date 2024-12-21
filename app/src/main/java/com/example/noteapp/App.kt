package com.example.noteapp

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.db.AppDataBase
import com.example.noteapp.utlis.PreferenceHelper

class App : Application() {
    companion object {
        var appDatabase: AppDataBase? = null
    }
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
        getInstance()
    }

    private fun getInstance(): AppDataBase? {
        if (appDatabase == null) {
            appDatabase = applicationContext?.let { context ->
                Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

            }
        }
        return appDatabase
    }
}