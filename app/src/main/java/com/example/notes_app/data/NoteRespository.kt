package com.example.notes_app

import androidx.lifecycle.LiveData
import com.example.notes_app.data.room.Notes
import com.example.notes_app.data.room.NotesDao

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getallNotes()

    fun insertNote(note: Notes) {
        notesDao.insertNote(note)
    }

    fun deleteNote(note: Notes) {
        notesDao.deleteNote(note);
    }

    fun updateNote(note: Notes) {
        notesDao.updateNote(note)
    }
}