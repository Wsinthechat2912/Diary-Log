package com.example.note.ui.theme.screen.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    repository: NoteRepository
) : ViewModel() {

    val noteList = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}