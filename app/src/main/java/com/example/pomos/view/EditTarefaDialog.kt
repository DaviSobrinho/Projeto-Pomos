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
import com.example.pomos.databinding.DialogEditTarefaBinding
import com.example.pomos.viewmodel.CarregaTarefaDialog
import com.example.pomos.viewmodel.ExcluirTarefaDialog
import com.example.pomos.viewmodel.SalvarTarefaDialog
import com.google.android.material.textfield.TextInputEditText

private var _binding: DialogEditTarefaBinding? = null
private val binding get() = _binding!!

class EditTarefaDialog(val nome: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditTarefaBinding.inflate(LayoutInflater.from(context))
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
        configuraBotaoLapis(binding.editTarefaDialogImagebutton2, binding.editTarefaDialogTextinputedittext1)
        configuraBotaoLapis(binding.editTarefaDialogImagebutton3, binding.editTarefaDialogTextinputedittext2)
        configuraBotaoLapis(binding.editTarefaDialogImagebutton4, binding.editTarefaDialogTextinputedittext4)
        configuraBotaoLapis(binding.editTarefaDialogImagebutton8, binding.editTarefaDialogTextinputedittext5)
        configuraSpinner(binding.editTarefaDialogSpinner2,requireContext(), listOf("Prioridade:","Alta","Média","Baixa"))
        configuraBotaoSpinner(binding.editTarefaDialogImagebutton5,binding.editTarefaDialogSpinner2)
        configuraBotaoContador(binding.editTarefaDialogImagebutton6, binding.editTarefaDialogTextinputedittext3, true)
        configuraBotaoContador(binding.editTarefaDialogImagebutton7, binding.editTarefaDialogTextinputedittext3, false)
        corrigePomodoros(binding.editTarefaDialogTextinputedittext3)
        configuraBotaoSair(binding.editTarefaDialogImagebutton1)
        configuraImagemSpiner(binding.editTarefaDialogImageview2,binding.editTarefaDialogSpinner2)
        binding.editTarefaDialogTextview2.setOnClickListener{
            binding.editTarefaDialogSpinner2.performClick()
        }
        val salva = SalvarTarefaDialog()
        val exclui = ExcluirTarefaDialog()
        val dialogv = dialog
        preencheDados()
        if (dialogv != null) {
            salva.salvaTarefa(binding.editTarefaDialogMaterialbutton2,
                binding.editTarefaDialogTextinputedittext1,
                binding.editTarefaDialogTextinputedittext2,
                binding.editTarefaDialogTextview1,
                binding.editTarefaDialogSpinner2,
                binding.editTarefaDialogTextinputedittext3,requireContext(),dialogv,
                binding.editTarefaDialogTextinputedittext4,
                binding.editTarefaDialogTextinputedittext5)
            exclui.excluiTarefa(binding.editTarefaDialogMaterialbutton1,binding.editTarefaDialogTextinputedittext1,requireContext(),dialogv)
        }
        corrigeTempo(binding.editTarefaDialogTextinputedittext4)
        corrigeTempo(binding.editTarefaDialogTextinputedittext5)
    }
    fun preencheDados(){
        binding.editTarefaDialogTextview1.text = nome
        val carregaTarefa = CarregaTarefaDialog(requireContext(), nome)
        binding.editTarefaDialogTextinputedittext1.setText(carregaTarefa.nome)
        binding.editTarefaDialogTextinputedittext2.setText(carregaTarefa.descricao)
        binding.editTarefaDialogTextinputedittext3.setText(carregaTarefa.pomodoros.toString())
        binding.editTarefaDialogTextinputedittext4.setText(carregaTarefa.foco.toString())
        binding.editTarefaDialogTextinputedittext5.setText(carregaTarefa.descanso.toString())
        if (carregaTarefa.prioridade == "Alta"){
            binding.editTarefaDialogSpinner2.setSelection(1)
            binding.editTarefaDialogImageview1.setImageResource(R.mipmap.redtag_foreground)
        }
        if (carregaTarefa.prioridade == "Média"){
            binding.editTarefaDialogSpinner2.setSelection(2)
            binding.editTarefaDialogImageview1.setImageResource(R.mipmap.yellowtag_foreground)
        }
        if (carregaTarefa.prioridade == "Baixa"){
            binding.editTarefaDialogSpinner2.setSelection(3)
            binding.editTarefaDialogImageview1.setImageResource(R.mipmap.greentag_foreground)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun corrigePomodoros (textInputEditText: TextInputEditText){
        binding.editTarefaDialogTextinputedittext3.addTextChangedListener(object : TextWatcher {
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
                    binding.editTarefaDialogImageview1.setImageResource(R.mipmap.redtag_foreground)
                }
                if (spinnerString == "Média"){
                    imageView.setImageResource(R.mipmap.yellowtag_foreground)
                    binding.editTarefaDialogImageview1.setImageResource(R.mipmap.yellowtag_foreground)
                }
                if (spinnerString == "Baixa"){
                    imageView.setImageResource(R.mipmap.greentag_foreground)
                    binding.editTarefaDialogImageview1.setImageResource(R.mipmap.greentag_foreground)
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
}