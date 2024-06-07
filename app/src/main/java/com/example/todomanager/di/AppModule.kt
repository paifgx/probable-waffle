package com.example.todomanager.di

import android.content.Context
import androidx.room.Room
import com.example.todomanager.data.ToDoDatabase
import com.example.todomanager.data.ToDoDao
import com.example.todomanager.repository.ToDoRepository
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
    fun provideDatabase(@ApplicationContext appContext: Context): ToDoDatabase =
        Room.databaseBuilder(appContext, ToDoDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideToDoDao(db: ToDoDatabase): ToDoDao =
        db.todoDao()

    @Provides
    @Singleton
    fun provideToDoRepository(db: ToDoDatabase): ToDoRepository =
        ToDoRepository(db.todoDao())

}