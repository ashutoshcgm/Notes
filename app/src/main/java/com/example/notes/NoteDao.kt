package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao

interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    To do task like insert and delete in background we add suspend at beginning  so that UI not become laggy
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes():LiveData<List<Note>>
}