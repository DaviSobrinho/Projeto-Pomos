package com.example.pomos.viewmodel

import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Spinner
import android.widget.Toast
import com.example.pomos.database.AppDatabase
import com.example.pomos.database.model.Tarefa
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

open class AdicionarTarefaDialog {
     fun AdicionaTarefa(
        botaoconfirma : MaterialButton, nome : TextInputEditText, descricao : TextInputEditText,
         prioridade : Spinner, pomodoros : TextInputEditText ,context: Context, dialog : Dialog,
        foco : TextInputEditText, descanso : TextInputEditText) {
        val db = AppDatabase.instancia(context)
        botaoconfirma.setOnClickListener() {
            val nomet = nome.text.toString()
            val descricaot = descricao.text.toString()
            val prioridadet = prioridade.selectedItem.toString()
            val pomodorost = pomodoros.text.toString().toInt()
            val focot = foco.text.toString().toInt()
            val descansot = descanso.text.toString().toInt()
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
                Toast.makeText(context, "Tarefa criada!!", Toast.LENGTH_SHORT).show()
                if (dialog.isShowing == true) {
                    dialog.dismiss()
                }
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (!imm.isActive) {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }
            } catch (exception: Exception) {
                Toast.makeText(
                    context,
                    "Já existe uma tarefa com esse nome, tente outro!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}