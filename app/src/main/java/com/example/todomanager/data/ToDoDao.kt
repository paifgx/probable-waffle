package com.example.todomanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todomanager.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert
    suspend fun insert(todo: ToDo)

    @Update
    suspend fun update(todo: ToDo)

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("DELETE FROM todo_table WHERE isCompleted = 1")
    suspend fun clearCompleted()

    @Query("SELECT * FROM todo_table ORDER BY dueDate ASC")
    fun getTodos(): Flow<List<ToDo>>

}
