package com.example.note.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.note.data.local.entity.dao.Notedao

@Database(
    version = 1,
    entities = [NoteEntity::class]
)
abstract class  NoteDatabase: RoomDatabase() {
    abstract val dao: Notedao
    companion object {
        const val name = "note_db"
    }
}