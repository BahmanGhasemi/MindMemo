package ir.bahmanghasemi.mindmemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.bahmanghasemi.mindmemo.feature_note.data.data_source.NoteDatabase
import ir.bahmanghasemi.mindmemo.feature_note.data.repository.NoteRepositoryImpl
import ir.bahmanghasemi.mindmemo.feature_note.domain.repository.NoteRepository
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.AddNoteUseCase
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.DeleteNoteUseCase
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.GetNoteUseCase
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.GetNotesUseCase
import ir.bahmanghasemi.mindmemo.feature_note.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            AddNoteUseCase(repository),
            GetNoteUseCase(repository),
            GetNotesUseCase(repository),
            DeleteNoteUseCase(repository)
        )
    }
}