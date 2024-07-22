package ir.bahmanghasemi.mindmemo.feature_note.domain.use_case

import com.google.common.truth.Truth
import ir.bahmanghasemi.mindmemo.feature_note.data.repository.TestNoteRepository
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.InvalidNoteException
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {
    lateinit var addUseCase: AddNoteUseCase
    lateinit var getUseCase: GetNotesUseCase

    @Before
    fun setup() {
        val repository = TestNoteRepository()
        addUseCase = AddNoteUseCase(repository)
        getUseCase = GetNotesUseCase(repository)

        val list = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            list.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }
        list.shuffle()
        runBlocking {
            list.forEach {
                repository.insertNote(it)
            }
        }
    }

    @Test
    fun `Note with empty title, return false`(): Unit = runBlocking {
        val note = Note(
            title = "",
            content = "content value",
            timeStamp = 1L,
            color = 2,
            id = 3
        )

        val result = try {
            addUseCase(note)
            true
        } catch (e: InvalidNoteException) {
            false
        }

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Note with empty content, return false`(): Unit = runBlocking {
        val note = Note(
            title = "title value",
            content = "",
            timeStamp = 1L,
            color = 2,
            id = 3
        )

        val result = try {
            addUseCase(note)
            true
        } catch (e: InvalidNoteException) {
            false
        }

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Note with valid title and content, return true`(): Unit = runBlocking {
        val note = Note(
            title = "title value",
            content = "content value",
            timeStamp = 1L,
            color = 2,
            id = 3
        )

        val result = try {
            addUseCase(note)
            true
        } catch (e: InvalidNoteException) {
            false
        }

        Truth.assertThat(result).isTrue()
    }

}