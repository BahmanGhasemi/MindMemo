package ir.bahmanghasemi.mindmemo.feature_note.domain.use_case

data class NoteUseCases(
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)
