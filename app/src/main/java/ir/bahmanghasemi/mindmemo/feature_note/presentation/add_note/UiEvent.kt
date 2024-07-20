package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note

sealed class UiEvent {
    data class Failure(val message: String) : UiEvent()
    data object Success : UiEvent()
}