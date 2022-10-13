package com.example.pomos.viewmodel

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.button.MaterialButton

open class ExcluirTarefaDialog {
    fun excluirTarefa(button: MaterialButton){
        button.setOnClickListener {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(!imm.isActive){
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }
    }
}