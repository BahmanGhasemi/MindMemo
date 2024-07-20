package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note

sealed class AddNoteEvent {
    data class SetTitle(val title: String) : AddNoteEvent()
    data class SetContent(val content: String) : AddNoteEvent()
    data class SetColor(val color: Int) : AddNoteEvent()
    data class TimeStamp(val timeStamp: Long) : AddNoteEvent()
    data class SetId(val id: Int) : AddNoteEvent()
    data class GetNote(val id: Int) : AddNoteEvent()
    data object Save : AddNoteEvent()
}