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
        botaosalva : MaterialButton, nome : TextInputEditText, descricao : TextInputEditText, titulo : TextView, prioridade : Spinner, pomodoros : TextInputEditText, context: Context, dialog : Dialog,
    foco : TextInputEditText, descanso : TextInputEditText) {
        var nomeSalvo = nome.text.toString()
        val db = AppDatabase.instancia(context)
        botaosalva.setOnClickListener() {
            if (nome.text.toString().replace(" ", "") == ""|| nome.text.toString().isEmpty()){
                Toast.makeText(
                    context,
                    "Por favor, insira um nome para sua tarefa",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val nomet = nome.text.toString()
            val descricaot = descricao.text.toString()
            val prioridadet = prioridade.selectedItem.toString()
            val pomodorost = pomodoros.text.toString().toInt()
            if(foco.text.toString() == ""){
                Toast.makeText(context, "Por favor, insira um tempo de foco válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val focot = foco.text.toString()
            if(descanso.text.toString() == ""){
                Toast.makeText(context, "Por favor, insira um tempo de descanso válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val descansot = descanso.text.toString()
            if(descansot.length == 5 && focot.length == 5) {
                try {
                    db.funDao().insertTarefa(
                        Tarefa(
                            nome = nomet,
                            pomodoros = pomodorost,
                            descricao = descricaot,
                            prioridade = prioridadet,
                            foco = focot,
                            descanso = descansot
                        )
                    )
                    Toast.makeText(context, "Tarefa atualizada!!", Toast.LENGTH_SHORT).show()
                    db.funDao().deleteTarefa(db.funDao().queryTarefaByName(nomeSalvo))
                    nomeSalvo = nomet
                    titulo.text = nomet
                } catch (exception: Exception) {
                    if (nomeSalvo == nomet) {
                        db.funDao().updateTarefa(
                            Tarefa(
                                nome = nomet,
                                pomodoros = pomodorost,
                                descricao = descricaot,
                                prioridade = prioridadet,
                                foco = focot,
                                descanso = descansot
                            )
                        )
                        Toast.makeText(context, "Tarefa atualizada!!", Toast.LENGTH_SHORT).show()
                        val imm =
                            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
            }else{
                if (focot.length != 5 && descansot.length ==5){
                    Toast.makeText(context, "Por favor, insira um tempo de foco válido", Toast.LENGTH_SHORT).show()
                }else{
                    if (descansot.length != 5 && focot.length == 5){
                        Toast.makeText(context, "Por favor, insira um tempo de descanso válido", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Por favor, insira tempos de foco e de descanso válidos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}