package com.example.notes_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes_app.data.room.NoteDatabase
import com.example.notes_app.NoteRepository
import com.example.notes_app.data.room.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    var allNotes: LiveData<List<Notes>>
    private val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun updateNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun addNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
}