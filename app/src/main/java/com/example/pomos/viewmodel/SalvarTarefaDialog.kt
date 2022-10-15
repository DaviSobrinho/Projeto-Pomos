package com.example.pomos.viewmodel

import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.pomos.database.AppDatabase
import com.example.pomos.database.model.Tarefa
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SalvarTarefaDialog {
    fun salvaTarefa(
        botaosalva : MaterialButton, nome : TextInputEditText, descricao : TextInputEditText, titulo : TextView,
        projeto : Spinner, prioridade : Spinner, pomodoros : TextInputEditText, context: Context, dialog : Dialog
    ) {
        var nomeSalvo = nome.text.toString()
        val db = AppDatabase.instancia(context)
        botaosalva.setOnClickListener() {
            val nomet = nome.text.toString()
            val descricaot = descricao.text.toString()
            val projetot = projeto.selectedItem.toString()
            val prioridadet = prioridade.selectedItem.toString()
            val pomodorost = pomodoros.text.toString().toInt()
            try{
                db.funDao().insertTarefa(
                    Tarefa(
                        nome = nomet,
                        pomodoros = pomodorost,
                        projeto = projetot,
                        descricao = descricaot,
                        prioridade = prioridadet
                    )
                )
                Toast.makeText(context, "Tarefa atualizada!!", Toast.LENGTH_SHORT).show()
                db.funDao().deleteTarefa(db.funDao().queryTarefaByName(nomeSalvo))
                nomeSalvo = nomet
                titulo.text = nomet
            }catch (exception : Exception){
                if (nomeSalvo == nomet){
                    db.funDao().updateTarefa(
                        Tarefa(
                            nome = nomet,
                            pomodoros = pomodorost,
                            projeto = projetot,
                            descricao = descricaot,
                            prioridade = prioridadet
                        )
                    )
                    Toast.makeText(context, "Tarefa atualizada!!", Toast.LENGTH_SHORT).show()
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (!imm.isActive) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    }
                    titulo.text = nomet
                    return@setOnClickListener
                }
                Toast.makeText(
                    context,
                    "Já existe uma tarefa com esse nome, tente outro!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}