package com.example.note.data.local.entity.dao

import android.content.Context
import androidx.room.Room
import com.example.note.data.local.entity.NoteDatabase
import com.example.note.data.local.entity.repository.NRepositoryImpl
import com.example.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.name
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NRepositoryImpl(dao = database.dao)
    }
}
