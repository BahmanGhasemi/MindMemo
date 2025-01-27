package ir.bahmanghasemi.mindmemo.feature_note.data.repository

import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import ir.bahmanghasemi.mindmemo.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }
}