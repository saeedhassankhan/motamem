package org.matamem.ui.groups.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.matamem.modals.Note
import org.matamem.repository.Repository
import javax.inject.Inject


@HiltViewModel
class ActivityGroupNotesViewModel @Inject constructor(var repository: Repository) : ViewModel() {
    lateinit var notes: LiveData<List<Note>>

    fun getTexts(id: Int) {
        notes = repository.getNotes(id)
    }

    fun updateText(note: Note) {
        repository.updateNote(note)
    }

    fun deleteText(note: Note) {
        repository.deleteNote(note);
    }
}