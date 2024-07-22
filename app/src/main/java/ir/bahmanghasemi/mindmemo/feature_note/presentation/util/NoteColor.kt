package ir.bahmanghasemi.mindmemo.feature_note.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

enum class NoteColor(val color: Color) {
    White(Color(0xFFFFFFFF)),
    Orange(Color(0xFFFFA447)),
    SkyBlue(Color(0xFF7ECBFF)),
    RedPink(Color(0xFFFFA6C4)),
    Teal(Color(0xFF1ECCC3)),
    RedOrange(Color(0xFFFFA3A3));

    fun randomColor(): Int = NoteColor.entries.random().color.toArgb()
}
