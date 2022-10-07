package com.example.pomos.viewmodel

import android.accessibilityservice.AccessibilityService
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.input.InputManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.example.pomos.R
import com.example.pomos.databinding.DialogAddTarefaBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


private var _binding: DialogAddTarefaBinding? = null
private val binding get() = _binding!!

class AddTarefaDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            _binding = DialogAddTarefaBinding.inflate(LayoutInflater.from(context))
            return AlertDialog.Builder(requireActivity())
                .setView(binding.root)
                .create()
        }

        override fun onStart() {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            super.onStart()
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.80).toInt()
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            configuraBotaoLapis(binding.imagebutton2, binding.textinputedittext1)
            configuraBotaoLapis(binding.imagebutton3, binding.textinputedittext2)
            configuraSpinner(binding.spinner1,requireContext(), listOf("Adicionar ao projeto:","Projeto 1", "Projeto 2"))
            configuraBotaoSpinner(binding.imagebutton4,binding.spinner1)
            configuraSpinnerImagem(binding.spinner2,requireContext(), listOf("Alta","Média","Baixa"))
            configuraBotaoSpinner(binding.imagebutton5,binding.spinner2)
            configuraBotaoContador(binding.imagebutton6, binding.textinputedittext3, true)
            configuraBotaoContador(binding.imagebutton7, binding.textinputedittext3, false)
            corrigePomodoros(binding.textinputedittext3)
            configuraBotaoSair(binding.imagebutton1)
            configuraBotaoCancelar(binding.materialbutton1)
        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
        private fun corrigePomodoros (textInputEditText: TextInputEditText){
            binding.textinputedittext3.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    var num = 0
                    val a = 0
                    if(textInputEditText.text.toString().startsWith("0") && textInputEditText.text.toString().length >1){
                        var text = textInputEditText.text.toString()
                        text = text.replace("0","")
                        textInputEditText.setText(text)
                    }
                    if (textInputEditText.text.toString() == ""){
                        num = 0
                        textInputEditText.setText(num.toString())
                        return
                    }
                    if (textInputEditText.text.toString().toInt() < 0){
                        num = 0
                        textInputEditText.setText(num.toString())
                    }

                    if(textInputEditText.text.toString().length >1 && textInputEditText.text.toString() != "10"){
                        var text = textInputEditText.text.toString()
                        text = text.takeLast(1)
                        textInputEditText.setText(text)
                    }
                    textInputEditText.setSelection(textInputEditText.text.toString().lastIndex+1)
                    return
                }
            })
        }
        private fun configuraBotaoContador(button: ImageButton,textInputEditText: TextInputEditText,boolean: Boolean){
            if (boolean){
                button.setOnClickListener(){
                    val textInputEditTextString = textInputEditText.text.toString()
                    val textInputEditTextInt = textInputEditTextString.toInt()+1
                    if (textInputEditTextInt != 11){
                        textInputEditText.setText(textInputEditTextInt.toString())
                    }
                }
            }else{
                button.setOnClickListener(){
                    val textInputEditTextString = textInputEditText.text.toString()
                    val textInputEditTextInt = textInputEditTextString.toInt()-1
                    if (textInputEditTextInt != -1){
                        textInputEditText.setText(textInputEditTextInt.toString())
                    }
                }
            }

        }
        private fun configuraBotaoLapis(button: ImageButton, textInputEditText: TextInputEditText){
            button.setOnClickListener {
                textInputEditText.requestFocus()
                if(textInputEditText.hasFocus()){
                    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1)
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 2)
            }
        }
        private fun configuraBotaoSpinner(button: ImageButton, spinner: Spinner){
            button.setOnClickListener {
                spinner.performClick()
            }
        }
        private fun configuraSpinner(spinner: Spinner, context: Context, list: List<String>){
            val arrayAdapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
            spinner.adapter = arrayAdapter
        }
        private fun configuraSpinnerImagem(spinner: Spinner, context: Context, list: List<String>){
            val arrayAdapter = ArrayAdapter(context, R.layout.add_tarefa_dialog_spinner_layout,R.id.add_tarefa_dialog_textview_1,list)
            spinner.adapter = arrayAdapter
        }
        private fun configuraBotaoSair(button: ImageButton){
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
        private fun configuraBotaoCancelar(button: MaterialButton){
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