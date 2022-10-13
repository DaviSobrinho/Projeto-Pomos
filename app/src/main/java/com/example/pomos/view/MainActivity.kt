package com.example.pomos.view

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pomos.R
import com.example.pomos.database.AppDatabase
import com.example.pomos.databinding.ActivityMainBinding
import com.example.pomos.viewmodel.MainActivityRecyclerViewAdapter

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.activityMainMaterialbutton1.setOnClickListener(){
            AddTarefaDialog().show(supportFragmentManager, "MyCustomFragment")
        }
        configuraRecyclerView()
    }
    fun configuraRecyclerView(){
        val adapter = MainActivityRecyclerViewAdapter(context = this)
        val db = AppDatabase.instancia(this)
        adapter.refresh(db.funDao().queryAllTarefa())
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_recyclerview_1 )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}