package com.example.pomos.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.pomos.databinding.ActivityMainBinding
import com.example.pomos.databinding.DialogAddTarefaBinding
import com.example.pomos.viewmodel.AddTarefaDialog

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.button1.setOnClickListener(){
            AddTarefaDialog().show(supportFragmentManager, "MyCustomFragment")
        }



    }
    /*fun configuraBotaoAdicionarTarefa(){
        binding.button1.setOnClickListener(){
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show()
            ADDTarefaDialogFragment(1)
        }
    }*/

}