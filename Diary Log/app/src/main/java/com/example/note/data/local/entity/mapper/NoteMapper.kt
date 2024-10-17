package com.example.note.data.local.entity.mapper

import com.example.note.data.local.entity.NoteEntity
import com.example.note.domain.model.Note

fun NoteEntity.asExternalmodel(): Note = Note (
    id, title, content
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id, title, commentor
)
