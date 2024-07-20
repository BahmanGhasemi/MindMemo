package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note

import androidx.compose.ui.graphics.toArgb
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.NoteColor
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.TimeUtil

data class AddNoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = NoteColor.entries.random().color.toArgb(),
    val timeStamp: Long = TimeUtil.toUtc(),
    val id: Int? = null
)