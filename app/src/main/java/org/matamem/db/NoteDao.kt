package org.matamem.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.matamem.modals.Note

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note?)


    @Query("SELECT * FROM note_table WHERE group_id = :groupId")
    fun getNotes(groupId : Int) : LiveData<List<Note>>

    @Update
    fun updateNote(note : Note)

    @Delete
    fun deleteNote(note : Note)
}