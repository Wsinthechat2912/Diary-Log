package com.example.note.ui.theme.screen.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.domain.model.Note
import com.example.note.domain.repository.NoteRepository
import com.example.note.ui.theme.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    init {
        savedStateHandle.get<String>("id")?.let {
            val id = it.toInt()
            viewModelScope.launch {
                repository.getNoteById(id)?.let { note ->
                    _state.update { screenState ->
                        screenState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.commentor
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ContentChange -> {
                _state.update {
                    it.copy(
                        content = event.value
                    )
                }
            }

            is NoteEvent.TitleChange -> {
                _state.update {
                    it.copy(
                        title = event.value
                    )
                }
            }

            NoteEvent.NavigateBack -> sendEvent(UiEvent.NavigateBack)
            NoteEvent.Save -> {
                viewModelScope.launch {
                    val state = state.value
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        commentor = state.content
                    )
                    if (state.id == null) {
                        repository.insertNote(note)
                    } else {
                        repository.updateNote(note)
                    }
                    sendEvent(UiEvent.NavigateBack)
                }
            }

            NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    val state = state.value
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        commentor = state.content
                    )
                    repository.deleteNote(note)
                }
                sendEvent(UiEvent.NavigateBack)
            }
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }
}