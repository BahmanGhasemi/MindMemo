package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.InvalidNoteException
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    var state = mutableStateOf(AddNoteState())
        private set

    val oneTimeEvent = MutableSharedFlow<UiEvent>()

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.SetTitle -> {
                state.value = state.value.copy(title = event.title)
            }

            is AddNoteEvent.SetContent -> {
                state.value = state.value.copy(content = event.content)
            }

            is AddNoteEvent.SetColor -> {
                state.value = state.value.copy(color = event.color)
            }

            is AddNoteEvent.TimeStamp -> {
                state.value = state.value.copy(timeStamp = event.timeStamp)
            }

            is AddNoteEvent.SetId -> {
                state.value = state.value.copy(id = event.id)
            }

            is AddNoteEvent.GetNote -> {
                viewModelScope.launch {
                    useCases.getNoteUseCase(event.id)?.let { note ->
                        state.value = state.value.copy(
                            title = note.title,
                            content = note.content,
                            color = note.color,
                            timeStamp = note.timeStamp
                        )
                    }
                }
            }

            AddNoteEvent.Save -> {
                viewModelScope.launch {
                    try {
                        useCases.addNoteUseCase(
                            Note(
                                title = state.value.title,
                                content = state.value.content,
                                color = state.value.color,
                                timeStamp = state.value.timeStamp,
                                id = state.value.id
                            )
                        )
                        oneTimeEvent.emit(UiEvent.Success)
                    } catch (e: InvalidNoteException) {
                        oneTimeEvent.emit(
                            UiEvent.Failure(
                                message = e.message ?: "Unknown Error Occurred!"
                            )
                        )
                    }
                }
            }
        }
    }
}