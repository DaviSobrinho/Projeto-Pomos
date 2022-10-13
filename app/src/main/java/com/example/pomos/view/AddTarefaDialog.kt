package com.example.pomos.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.pomos.R
import com.example.pomos.database.AppDatabase
import com.example.pomos.database.model.Tarefa
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
            configuraSpinner(binding.spinner2,requireContext(), listOf("Prioridade:","Alta","Média","Baixa"))
            configuraBotaoSpinner(binding.imagebutton5,binding.spinner2)
            configuraBotaoContador(binding.imagebutton6, binding.textinputedittext3, true)
            configuraBotaoContador(binding.imagebutton7, binding.textinputedittext3, false)
            corrigePomodoros(binding.textinputedittext3)
            configuraBotaoSair(binding.imagebutton1)
            configuraBotaoCancelar(binding.materialbutton1)
            configuraImagemSpiner(binding.imagebutton8,binding.spinner2)
            salvaTarefa()
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
        private fun configuraImagemSpiner(imageButton: ImageButton, spinner: Spinner){
            imageButton.setOnClickListener(){
                configuraBotaoSpinner(imageButton,spinner)
            }
            var spinnerString = spinner.selectedItem.toString()
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    spinnerString = spinner.selectedItem.toString()
                    if (spinnerString == "Alta"){
                        imageButton.setImageResource(R.drawable.redflag_foreground)
                        Toast.makeText(requireContext(), "teste", Toast.LENGTH_SHORT).show()
                    }
                    if (spinnerString == "Média"){
                        imageButton.setImageResource(R.drawable.yellowflag_foreground)
                    }
                    if (spinnerString == "Baixa"){
                        imageButton.setImageResource(R.drawable.greenflag_foreground)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
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
        private fun salvaTarefa() {
            val db = AppDatabase.instancia(requireContext())
            binding.materialbutton2.setOnClickListener() {
                val nome = binding.textinputedittext1.text.toString()
                val descricao = binding.textinputedittext2.text.toString()
                val projeto = binding.spinner1.selectedItem.toString()
                val prioridade = binding.spinner2.selectedItem.toString()
                val pomodoros = binding.textinputedittext3.text.toString().toInt()
                try{
                    val tarefa = db.funDao().queryTarefaByName(nome)
                    db.funDao().insertTarefa(Tarefa(
                            nome = nome,
                            pomodoros = pomodoros,
                            projeto = projeto,
                            descricao = descricao,
                            prioridade = prioridade
                        )
                    )
                    Toast.makeText(requireContext(), "Tarefa criada!!", Toast.LENGTH_SHORT).show()
                    if (dialog?.isShowing == true) {
                        dialog?.dismiss()
                    }
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if(!imm.isActive){
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    }
                }catch(exception : Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Já existe uma tarefa com esse nome, tente outro!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}