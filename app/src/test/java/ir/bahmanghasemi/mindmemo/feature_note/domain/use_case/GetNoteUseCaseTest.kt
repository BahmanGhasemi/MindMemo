package ir.bahmanghasemi.mindmemo.feature_note.domain.use_case

import com.google.common.truth.Truth
import ir.bahmanghasemi.mindmemo.feature_note.data.repository.TestNoteRepository
import ir.bahmanghasemi.mindmemo.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteUseCaseTest {

    lateinit var repository: TestNoteRepository
    lateinit var useCase: GetNoteUseCase

    @Before
    fun setup() {
        repository = TestNoteRepository()
        useCase = GetNoteUseCase(repository)

        val notes = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notes.add(
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
            notes.forEach {
                repository.insertNote(it)
            }
        }
    }

    @Test
    fun `Find note by valid id, correct`(): Unit = runBlocking {
        val note = useCase(10)
        Truth.assertThat(note?.title).isEqualTo("k")
    }

    @Test
    fun `Find note by invalid id, return null`(): Unit = runBlocking {
        val note = useCase(100)
        Truth.assertThat(note?.title).isNull()
    }

}