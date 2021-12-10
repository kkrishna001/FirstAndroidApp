package com.example.notes_app.data.room


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(notes: Notes)

    @Delete
    fun deleteNote(notes: Notes)

    @Query("SELECT * FROM notes_container ORDER BY id ASC")
    fun getallNotes(): LiveData<List<Notes>>

    @Update
    fun updateNote(notes: Notes)

}