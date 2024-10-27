package com.example.midterm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    var title: String,
    var description: String,
    var isCompleted: Boolean = false
)
