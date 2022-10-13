package com.example.pomos.database.dao

import androidx.room.*
import com.example.pomos.database.model.Tarefa
@Dao

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