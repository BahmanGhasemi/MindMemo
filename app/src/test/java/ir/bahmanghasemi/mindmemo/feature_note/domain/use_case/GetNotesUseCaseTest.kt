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

class GetNotesUseCaseTest() {

    lateinit var useCase: GetNotesUseCase
    lateinit var repository: TestNoteRepository

    @Before
    fun setup() {
        repository = TestNoteRepository()
        useCase = GetNotesUseCase(repository)

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
    fun `Order note by title ascending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            Truth.assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order by title descending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size - 2)
            Truth.assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
    }

    @Test
    fun `Order by date ascending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2)
            Truth.assertThat(notes[i].timeStamp).isLessThan(notes[i + 1].timeStamp)
    }

    @Test
    fun `Order by date descending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2)
            Truth.assertThat(notes[i].timeStamp).isGreaterThan(notes[i + 1].timeStamp)
    }

    @Test
    fun `Order by color ascending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2)
            Truth.assertThat(notes[i].color).isLessThan(notes[i + 1].color)
    }

    @Test
    fun `Order by color descending, correct`() = runBlocking {
        val notes = useCase(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2)
            Truth.assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
    }
}