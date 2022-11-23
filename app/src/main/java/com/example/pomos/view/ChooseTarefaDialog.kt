package com.example.pomos.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.pomos.R
import com.example.pomos.database.AppDatabase
import com.example.pomos.database.model.Tarefa
import com.example.pomos.databinding.DialogChooseTarefaBinding
import com.example.pomos.viewmodel.CarregaTarefaDialog
import com.google.android.material.button.MaterialButton

private var _binding: DialogChooseTarefaBinding? = null
private val binding get() = _binding!!

class ChooseTarefaDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogChooseTarefaBinding.inflate(LayoutInflater.from(context))
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
        val dialogv = dialog
        val db = AppDatabase.instancia(requireContext())
        val spinnerList : List<Tarefa> = db.funDao().queryAllTarefa()
        val range = spinnerList.size
        var contador = 0
        var spinnerListString = ArrayList<String>()
        while(contador < range){
            spinnerListString.add(spinnerList.elementAt(contador).nome)
            contador++
        }

        binding.chooseTarefaDialogTextinputedittext1.showSoftInputOnFocus = false
        binding.chooseTarefaDialogTextinputedittext2.showSoftInputOnFocus = false
        binding.chooseTarefaDialogTextinputedittext3.showSoftInputOnFocus = false
        binding.chooseTarefaDialogTextinputedittext4.showSoftInputOnFocus = false

        binding.chooseTarefaDialogTextview2.setOnClickListener{
            binding.chooseTarefaDialogSpinner1.performClick()
        }
        binding.chooseTarefaDialogImagebutton2.setOnClickListener{
            binding.chooseTarefaDialogSpinner1.performClick()
        }
        configuraSpinner(binding.chooseTarefaDialogImageview2,binding.chooseTarefaDialogSpinner1,requireContext(),spinnerListString)
        try{
            preencheDados()
        }catch (exception : Exception){

        }
        configuraBotaoSair(binding.chooseTarefaDialogImagebutton1)
        configuraBotaoConfirmar(binding.chooseTarefaDialogMaterialbutton2)
        configuraBotaoCancelar(binding.chooseTarefaDialogMaterialbutton1)
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
    private fun configuraBotaoConfirmar(materialButton: MaterialButton){
        materialButton.setOnClickListener() {
            val name = binding.chooseTarefaDialogSpinner1.selectedItem.toString()
            val ciclos = binding.chooseTarefaDialogTextinputedittext2.text.toString().toInt()
            val foco = binding.chooseTarefaDialogTextinputedittext3.text.toString()
            val descanso = binding.chooseTarefaDialogTextinputedittext4.text.toString()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
            if (dialog?.isShowing == true) {
                (activity as MainActivity?)!!.trocaTarefa(name,ciclos,foco,descanso)
                dialog?.dismiss()
            }
        }
    }
    private fun configuraBotaoCancelar(materialButton: MaterialButton){
        materialButton.setOnClickListener() {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }
    }
    private fun configuraSpinner(imageView: ImageView,spinner: Spinner, context: Context, list: List<String>){
        val arrayAdapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val spinnerString = spinner.selectedItem.toString()
                preencheDados()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    fun preencheDados(){
        val nome = binding.chooseTarefaDialogSpinner1.selectedItem.toString()
        val carregaTarefa = CarregaTarefaDialog(requireContext(), nome)
        binding.chooseTarefaDialogTextview4.setText(carregaTarefa.prioridade)
        binding.chooseTarefaDialogTextinputedittext1.setText(carregaTarefa.descricao)
        binding.chooseTarefaDialogTextinputedittext2.setText(carregaTarefa.pomodoros.toString())
        binding.chooseTarefaDialogTextinputedittext3.setText(carregaTarefa.foco.toString())
        binding.chooseTarefaDialogTextinputedittext4.setText(carregaTarefa.descanso.toString())
        if (carregaTarefa.prioridade == "Alta"){
            binding.chooseTarefaDialogImageview1.setImageResource(R.mipmap.redtag_foreground)
            binding.chooseTarefaDialogImageview2.setImageResource(R.mipmap.redtag_foreground)
        }
        if (carregaTarefa.prioridade == "Média"){
            binding.chooseTarefaDialogImageview1.setImageResource(R.mipmap.yellowtag_foreground)
            binding.chooseTarefaDialogImageview2.setImageResource(R.mipmap.yellowtag_foreground)
        }
        if (carregaTarefa.prioridade == "Baixa"){
            binding.chooseTarefaDialogImageview1.setImageResource(R.mipmap.greentag_foreground)
            binding.chooseTarefaDialogImageview2.setImageResource(R.mipmap.greentag_foreground)
        }
    }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}