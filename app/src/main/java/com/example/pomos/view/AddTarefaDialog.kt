package com.example.pomos.view

import DurationPicker
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
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
import com.example.pomos.databinding.DialogAddTarefaBinding
import com.example.pomos.viewmodel.AdicionarTarefaDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.time.Duration
import java.util.*


private var _binding: DialogAddTarefaBinding? = null
private val binding get() = _binding!!

class AddTarefaDialog : DialogFragment() {


        val minute : Int = 0
        val second : Int = 0
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            _binding = DialogAddTarefaBinding.inflate(LayoutInflater.from(context))
            return AlertDialog.Builder(requireActivity())
                .setView(binding.root)
                .create()
        }
        override fun onStart() {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            super.onStart()
            val width = (resources.displayMetrics.widthPixels * 0.96).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.96).toInt()
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            configuraBotaoLapis(binding.addTarefaDialogImagebutton2, binding.addTarefaDialogTextinputedittext1)
            configuraBotaoLapis(binding.addTarefaDialogImagebutton3, binding.addTarefaDialogTextinputedittext2)
            configuraBotaoLapis(binding.addTarefaDialogImagebutton4,binding.addTarefaDialogTextinputedittext4)
            configuraBotaoLapis(binding.addTarefaDialogImagebutton8,binding.addTarefaDialogTextinputedittext5)
            configuraSpinner(binding.addTarefaDialogSpinner2,requireContext(), listOf("Alta","Média","Baixa"))
            configuraBotaoSpinner(binding.addTarefaDialogImagebutton5,binding.addTarefaDialogSpinner2)
            configuraBotaoContador(binding.addTarefaDialogImagebutton6, binding.addTarefaDialogTextinputedittext3, true)
            configuraBotaoContador(binding.addTarefaDialogImagebutton7, binding.addTarefaDialogTextinputedittext3, false)
            corrigePomodoros(binding.addTarefaDialogTextinputedittext3)
            configuraBotaoSair(binding.addTarefaDialogImagebutton1)
            configuraBotaoCancelar(binding.addTarefaDialogMaterialbutton1)
            configuraImagemSpiner(binding.addTarefaDialogImageview1,binding.addTarefaDialogSpinner2)
            binding.addTarefaDialogTextview2.setOnClickListener{
                binding.addTarefaDialogSpinner2.performClick()
            }
            val add = AdicionarTarefaDialog()
            val dialogv = dialog
            if (dialogv != null) {
                add.AdicionaTarefa(binding.addTarefaDialogMaterialbutton2,
                    binding.addTarefaDialogTextinputedittext1,
                    binding.addTarefaDialogTextinputedittext2,
                    binding.addTarefaDialogSpinner2,
                    binding.addTarefaDialogTextinputedittext3,requireContext(),dialogv,
                    binding.addTarefaDialogTextinputedittext4,
                    binding.addTarefaDialogTextinputedittext5)
            }
            corrigeTempo(binding.addTarefaDialogTextinputedittext5)
            corrigeTempo(binding.addTarefaDialogTextinputedittext4)
        }



        private fun corrigeTempo (textInputEditText: TextInputEditText){
            textInputEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    textInputEditText.removeTextChangedListener(this)
                    var text = textInputEditText.text.toString().replace(":","")
                    if(text.length == 4 ){
                        text = text.replace(":","")
                        text = text.substring(0,2)+":"+text.substring(2)
                        textInputEditText.setText(text)
                        if(textInputEditText.text.toString().replace(":","").toInt() > 5959){
                            textInputEditText.setText("59:59")
                        }else{
                            val secs = textInputEditText.text.toString().substring(3,5).toInt()
                            if(secs > 59){
                                text = textInputEditText.text.toString().substring(0,3)+"59"
                                textInputEditText.setText(text)
                            }
                        }
                    }
                    if(text.length == 3 ){
                        text = text.replace(":","")
                        text = text.substring(0,2)+":"+text.substring(2)
                        textInputEditText.setText(text)
                    }
                    if(text.length >= 5 ){
                        text = text.replace(":","")
                        text = text.substring(0,2)+":"+text.substring(2,4)
                        textInputEditText.setText(text)
                        if(textInputEditText.text.toString().replace(":","").toInt() > 5959){
                            textInputEditText.setText("59:59")
                        }
                    }
                    if(text.isNotEmpty()){
                        textInputEditText.setSelection(textInputEditText.text.toString().lastIndex+1)
                    }
                    textInputEditText.addTextChangedListener(this)
                    return
                }
            })
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun corrigePomodoros (textInputEditText: TextInputEditText){
            binding.addTarefaDialogTextinputedittext3.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    var num = 0
                    if(textInputEditText.text.toString().startsWith("0") && textInputEditText.text.toString().length >1){
                        var text = textInputEditText.text.toString()
                        text = text.replace("0","")
                        textInputEditText.setText(text)
                    }

                    if(textInputEditText.text.toString().startsWith("0") && textInputEditText.text.toString().length == 1){
                        var text = textInputEditText.text.toString()
                        text = text.replace("0","1")
                        textInputEditText.setText(text)
                    }

                    if(textInputEditText.text.toString().length >1 && textInputEditText.text.toString() != "10"){
                        var text = textInputEditText.text.toString()
                        text = text.takeLast(1)
                        textInputEditText.setText(text)
                    }

                    if(textInputEditText.text?.isNotEmpty() == true){
                        textInputEditText.setSelection(textInputEditText.text.toString().lastIndex+1)
                    }
                    return
                }
            })
        }
        fun configuraBotaoContador(button: ImageButton,textInputEditText: TextInputEditText,boolean: Boolean){
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
        private fun configuraImagemSpiner(imageView: ImageView, spinner: Spinner){
            imageView.setOnClickListener(){
                spinner.performClick()
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val spinnerString = spinner.selectedItem.toString()
                    if (spinnerString == "Alta"){
                        imageView.setImageResource(R.mipmap.redtag_foreground)
                    }
                    if (spinnerString == "Média"){
                        imageView.setImageResource(R.mipmap.yellowtag_foreground)
                    }
                    if (spinnerString == "Baixa"){
                        imageView.setImageResource(R.mipmap.greentag_foreground)
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
}