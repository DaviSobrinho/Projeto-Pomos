package com.example.pomos.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tarefa(
    @PrimaryKey val nome : String,
    val descricao : String,
    val prioridade : String,
    val pomodoros : Int,
    val foco : String,
    val descanso : String,
)
