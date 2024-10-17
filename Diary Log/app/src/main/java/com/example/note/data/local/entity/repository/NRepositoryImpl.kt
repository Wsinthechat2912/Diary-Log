package com.example.note.data.local.entity.repository

import com.example.note.data.local.entity.dao.Notedao
import com.example.note.data.local.entity.mapper.asExternalmodel
import com.example.note.data.local.entity.mapper.toEntity
import com.example.note.domain.model.Note
import com.example.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NRepositoryImpl (
    private val dao: Notedao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
            .map { notes ->
                notes.map {
                    it.asExternalmodel()
                }

            }
    }
    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)?.asExternalmodel()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }
}