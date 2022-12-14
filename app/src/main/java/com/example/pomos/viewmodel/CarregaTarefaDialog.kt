package com.example.pomos.viewmodel

import android.content.Context
import android.widget.Toast
import com.example.pomos.database.AppDatabase

class CarregaTarefaDialog(context: Context, val nome: String) {

    val db = AppDatabase.instancia(context)
    val tarefa = db.funDao().queryTarefaByName(nome)
    val prioridade = tarefa.prioridade
    val descricao = tarefa.descricao
    val pomodoros = tarefa.pomodoros
    val foco = tarefa.foco
    val descanso = tarefa.descanso
}