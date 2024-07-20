package ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.mindmemo.R
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesEvent
import ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.NotesState
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    state: State<NotesState>,
    onEvent: (NotesEvent) -> Unit,
    onNavigate: (Int?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate(-1) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Note")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
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

            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(items = state.value.notes) {
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNavigate(it.id)
                            },
                        note = it,
                        onDeleteClick = {
                            onEvent(NotesEvent.DeleteNote(it))
                            scope.launch {
                                snackBarHostState.currentSnackbarData?.dismiss()

                                val result = snackBarHostState.showSnackbar(
                                    "Note item deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}