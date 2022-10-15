package com.example.pomos.viewmodel

import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pomos.database.AppDatabase
import com.example.pomos.database.model.Tarefa
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

open class ExcluirTarefaDialog {
    fun excluiTarefa(
        botaoexclui : MaterialButton, nome : TextInputEditText, context: Context, dialog : Dialog
    ) {
        val db = AppDatabase.instancia(context)
        botaoexclui.setOnClickListener() {
            val tarefa = db.funDao().queryTarefaByName(nome.text.toString())
            db.funDao().deleteTarefa(tarefa
            )
            Toast.makeText(context, "A tarefa foi excluida!!", Toast.LENGTH_SHORT).show()
            if (dialog.isShowing == true) {
                dialog.dismiss()
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }
    }
}