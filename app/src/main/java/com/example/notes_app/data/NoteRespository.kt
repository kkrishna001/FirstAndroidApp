package com.example.notes_app.data

import androidx.lifecycle.LiveData
import com.example.notes_app.data.room.Notes
import com.example.notes_app.data.room.NotesDao

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getallNotes()

    suspend fun insertNote(note: Notes) {
        notesDao.insertNote(note)
    }

    suspend fun deleteNote(note: Notes) {
        notesDao.deleteNote(note);
    }

    suspend fun updateNote(note: Notes) {
        notesDao.updateNote(note)
    }
}