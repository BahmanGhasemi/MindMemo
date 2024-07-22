package ir.bahmanghasemi.mindmemo.feature_note.domain.use_case

import com.google.common.truth.Truth
import ir.bahmanghasemi.mindmemo.feature_note.data.repository.TestNoteRepository
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.NoteOrder
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteUseCaseTest {

    lateinit var deleteUseCase: DeleteNoteUseCase
    lateinit var getUseCase: GetNotesUseCase

    @Before
    fun setup() {
        val repository = TestNoteRepository()
        deleteUseCase = DeleteNoteUseCase(repository)
        getUseCase = GetNotesUseCase(repository)


        val list = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            list.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index,
                    id = index
                )
            )
        }
        runBlocking {
            list.forEach {
                repository.insertNote(it)
            }
        }
    }

    @Test
    fun `Delete note with valid value, correct`(): Unit = runBlocking {
        val note = Note(
            title = "b",
            content = "b",
            timeStamp = 1L,
            color = 1,
            id = 1
        )
        deleteUseCase(note)
        val notes = getUseCase(NoteOrder.Title(OrderType.Ascending)).first()

        Truth.assertThat(notes.contains(note)).isFalse()
    }

    @Test
    fun `Delete note with invalid value, fail`(): Unit = runBlocking {
        val note = Note(
            title = "b",
            content = "b",
            timeStamp = 1L,
            color = 1,
            id = 11
        )
        deleteUseCase(note)
        val notes = getUseCase(NoteOrder.Title(OrderType.Ascending)).first()

        Truth.assertThat(notes.find { it.title == "b" }).isNotNull()
    }

}