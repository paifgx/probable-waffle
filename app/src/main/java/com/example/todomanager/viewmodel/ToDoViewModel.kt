package com.example.todomanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomanager.model.ToDo
import com.example.todomanager.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    val allTodos = repository.allTodos

    fun insert(todo: ToDo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: ToDo) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: ToDo) = viewModelScope.launch {
        repository.delete(todo)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun clearCompleted() = viewModelScope.launch {
        repository.clearCompleted()
    }

}