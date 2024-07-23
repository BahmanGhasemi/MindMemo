package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.composable

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.mindmemo.R
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.AddNoteEvent
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.AddNoteState
import ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.UiEvent
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.TimeUtil
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreen(
    state: State<AddNoteState>,
    oneTimeEvent: SharedFlow<UiEvent>,
    onEvent: (AddNoteEvent) -> Unit,
    onNavigateUp: () -> Unit,
    id: Int,
    colorId: Int
) {
    val scope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }
    val animatableBackground = remember {
        derivedStateOf {
            Animatable(Color(state.value.color))
        }
    }

    LaunchedEffect(key1 = id) {
        if (id != -1) {
            onEvent(AddNoteEvent.SetColor(colorId))
            onEvent(AddNoteEvent.SetId(id))
            onEvent(AddNoteEvent.GetNote(id))
        }
    }

    LaunchedEffect(key1 = true) {
        oneTimeEvent.collectLatest {
            when (it) {
                is UiEvent.Failure -> {
                    snackBarState.showSnackbar(message = it.message)
                }

                is UiEvent.Success -> onNavigateUp()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(AddNoteEvent.Save) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Rounded.SaveAs, contentDescription = "Save")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackBarState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(animatableBackground.value.value)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            ColorPicker(
                modifier = Modifier.fillMaxWidth(),
                selectedColor = state.value.color,
                onColorChange = {
                    scope.launch {
                        animatableBackground.value.animateTo(
                            targetValue = Color(it),
                            animationSpec = tween(durationMillis = 500)
                        )
                    }
                    onEvent(AddNoteEvent.SetColor(it))
                }
            )

            Text(
                text = TimeUtil.toLocalDate(state.value.timeStamp),
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            TransparentTextField(
                modifier = Modifier.fillMaxWidth().
                semantics {
                    contentDescription = "TitleTextField"
                },
                text = state.value.title,
                onTextChanged = { onEvent(AddNoteEvent.SetTitle(it)) },
                hint = stringResource(R.string.enter_title),
                textStyle = MaterialTheme.typography.titleMedium
            )

            TransparentTextField(
                modifier = Modifier.fillMaxSize().semantics {
                    contentDescription = "ContentTextField"
                },
                text = state.value.content,
                onTextChanged = { onEvent(AddNoteEvent.SetContent(it)) },
                hint = stringResource(R.string.enter_content_text),
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}