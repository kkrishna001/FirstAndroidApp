package com.example.notes_app.data.room


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("SELECT * FROM notes_container ORDER BY id ASC")
    fun getallNotes():LiveData<List<Notes>>

    @Update
    suspend fun updateNote(notes: Notes)

}