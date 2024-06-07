package com.example.todomanager.repository

import androidx.lifecycle.asLiveData
import com.example.todomanager.data.ToDoDao
import com.example.todomanager.model.ToDo

class ToDoRepository(private val todoDao: ToDoDao) {

    val allTodos = todoDao.getTodos().asLiveData()

    suspend fun insert(todo: ToDo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: ToDo) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: ToDo) {
        todoDao.deleteById(todo.id)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    suspend fun clearCompleted() {
        todoDao.clearCompleted()
    }

}