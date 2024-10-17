package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note.ui.theme.NoteTheme
import com.example.note.ui.theme.screen.note.NoteScreen
import com.example.note.ui.theme.screen.note.NoteViewModel
import com.example.note.ui.theme.screen.note_list.NoteListScreen
import com.example.note.ui.theme.screen.note_list.NoteListViewModel
import com.example.note.ui.theme.util.Route
import com.example.note.ui.theme.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.noteList
                ) {
                    composable(route = Route.noteList) {
                        val viewModel = hiltViewModel<NoteListViewModel>()
                        val noteList by viewModel.noteList.collectAsState()

                        NoteListScreen(
                            noteList = noteList,
                            onNoteClick = { note ->
                                navController.navigate(
                                    Route.note.replace(
                                        "{id}",
                                        note.id.toString()
                                    )
                                )
                            },
                            onAddNoteClick = {
                                navController.navigate(Route.note)
                            }
                        )
                    }

                    composable(route = Route.note) {
                        val viewModel = hiltViewModel<NoteViewModel>()
                        val state by viewModel.state.collectAsState()

                        LaunchedEffect(key1 = true) {
                            viewModel.event.collect { event ->
                                when (event) {
                                    is UiEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }
                                    else -> Unit
                                }
                            }
                        }

                        NoteScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}