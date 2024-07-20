package ir.bahmanghasemi.mindmemo.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id =:id")
    suspend fun getNoteById(id: Int): Note?
}