package ir.bahmanghasemi.mindmemo.feature_note.presentation.util

import kotlinx.serialization.Serializable

@Serializable
object Notes

@Serializable
data class AddNote(val id: Int)