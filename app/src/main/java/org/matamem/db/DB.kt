package org.matamem.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.matamem.modals.Group
import org.matamem.modals.Note

@Database(entities = [Group::class , Note::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {
    abstract fun groupDao(): GroupDao?
    abstract fun textDao(): NoteDao?
}