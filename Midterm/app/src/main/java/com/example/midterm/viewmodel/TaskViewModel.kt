package com.example.midterm.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.midterm.data.AppDatabase
import com.example.midterm.data.Task
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>get() = _tasks

    fun loadTasksForUser(username: String) {
        viewModelScope.launch {
            taskDao.getTasksForUser(username).collect {taskList ->
                _tasks.postValue(taskList)
            }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskDao.deleteTask(task)
    }
}