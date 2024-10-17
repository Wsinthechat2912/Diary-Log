package com.example.note.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.note.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Notedao {

    @Query("SELECT * FROM NoteEntity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM noteEntity WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert//(onConflict = onConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)


}