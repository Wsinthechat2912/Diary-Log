package com.example.note.ui.theme.util

interface UiEvent {
    data class Navigate(val route: String): UiEvent
    object NavigateBack : UiEvent
}