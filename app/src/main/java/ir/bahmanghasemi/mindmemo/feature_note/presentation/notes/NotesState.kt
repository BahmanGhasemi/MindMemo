package ir.bahmanghasemi.mindmemo.feature_note.presentation.notes

import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.NoteOrder
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)