package com.example.noteapp.ui.fragments.noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.ui.fragments.noteapp.data.database.daos.NoteDao

@Database(entities = [], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

}