package ir.bahmanghasemi.mindmemo.feature_note.domain.repository

import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun upsertNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
}