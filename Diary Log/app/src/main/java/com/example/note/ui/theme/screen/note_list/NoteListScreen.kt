package com.example.note.ui.theme.screen.note_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add note"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 25.dp,
                end = 25.dp,
                top = 15.dp + padding.calculateTopPadding(),
                bottom = 15.dp + padding.calculateBottomPadding()
            )
        ) {
            item {
                Text(
                    text = "Diary Log",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(noteList) { note ->
                ListItem(
                    headlineText = {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingText = {
                        Text(
                            text = note.commentor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    modifier = Modifier.clickable(
                        onClick = {
                            onNoteClick(note)
                        }
                    )
                )
            }
        }
    }
}
