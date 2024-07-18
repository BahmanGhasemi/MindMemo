package ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.mindmemo.R
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesEvent
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesState

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    state: MutableState<NotesState>,
    onEvent: (NotesEvent) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = { onEvent(NotesEvent.ToggleOrderSection) }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.Sort, contentDescription = "Sort")
            }
        }

        AnimatedVisibility(
            visible = state.value.isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            OrderSection(
                Modifier.fillMaxWidth(),
                noteOrder = state.value.noteOrder,
                onOrderChange = { onEvent(NotesEvent.Order(it)) }
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(items = state.value.notes, key = { it.id }) {
                NoteItem(
                    modifier = Modifier.fillMaxWidth(),
                    note = it,
                    onDeleteClick = { note ->
                        onEvent(NotesEvent.DeleteNote(note))
                    }
                )
            }
        }
    }
}