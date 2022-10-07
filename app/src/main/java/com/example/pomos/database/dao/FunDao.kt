package com.example.pomos.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pomos.database.model.Tarefa


interface FunDao {
    @Query("SELECT * FROM Tarefa")
    fun queryAllTarefa() :List<Tarefa>

    @Query("SELECT * FROM Tarefa WHERE nome = :nome")
    fun queryTarefaByName(nome: String?): List<Tarefa>

    @Insert
    fun insertTarefa(vararg Tarefa: Tarefa)

    @Delete
    fun deleteTarefa(vararg Tarefa: Tarefa)

    @Update
    fun updateTarefa(vararg Tarefa: Tarefa)
}