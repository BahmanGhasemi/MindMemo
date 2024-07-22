package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note

import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.NoteColor
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.TimeUtil

data class AddNoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = NoteColor.entries.random().randomColor(),
    val timeStamp: Long = TimeUtil.toUtc(),
    val id: Int? = null
)