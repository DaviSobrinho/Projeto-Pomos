package com.example.pomos.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tarefa(
    @PrimaryKey (autoGenerate = true)val id: Int,
    @PrimaryKey val nome : String,
    val descricao : String,
    val projeto : String,
    val prioridade : String,
    val pomodoros : Int
)
