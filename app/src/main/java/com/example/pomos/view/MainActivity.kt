package com.example.pomos.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import com.example.pomos.R
import com.example.pomos.databinding.ActivityMainBinding
import com.example.pomos.databinding.DialogAddTarefaBinding

private lateinit var binding: DialogAddTarefaBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddTarefaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    /*fun configuraBotaoAdicionarTarefa(){
        binding.button1.setOnClickListener(){
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show()
            ADDTarefaDialogFragment(1)
        }
    }*/

}