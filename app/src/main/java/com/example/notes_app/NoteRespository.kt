package com.example.notes_app

import androidx.lifecycle.LiveData

class NoteRespository (private val notesDao: NotesDao){

    val allNotes: List<Notes> = notesDao.getallNotes()

    suspend fun insertNote(note: Notes)
    {
        notesDao.insertNote(note)
    }
    suspend fun deleteNote(note: Notes)
    {
        notesDao.deleteNote(note);
    }
    suspend fun updateNote(note: Notes)
    {
        notesDao.updateNote(note)
    }
}