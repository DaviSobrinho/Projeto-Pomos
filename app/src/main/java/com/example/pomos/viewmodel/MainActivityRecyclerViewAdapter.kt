package com.example.pomos.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pomos.R
import com.example.pomos.database.model.Tarefa
import com.example.pomos.view.AddTarefaDialog


class MainActivityRecyclerViewAdapter(private val context: Context, tarefa: List<Tarefa> = emptyList(),
) : RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>() {
    private val tarefa = tarefa.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tarefa: Tarefa) {
            val nome = itemView.findViewById<TextView>(R.id.recyclerview_activity_main_textview_1)
            nome.text = (tarefa.nome)
            val prioridade =
                itemView.findViewById<ImageView>(R.id.recyclerview_activity_main_imageview_1)
            val prioridadeValidador = (tarefa.prioridade)
            if (prioridadeValidador == "Alta") {
                prioridade.setImageResource(R.drawable.redflag_foreground)
            }
            if (prioridadeValidador == "Média") {
                prioridade.setImageResource(R.drawable.yellowflag_foreground)
            }
            if (prioridadeValidador == "Baixa") {
                prioridade.setImageResource(R.drawable.greenflag_foreground)
            }
            val more = itemView.findViewById<ImageButton>(R.id.recyclerview_activity_main_imagebutton_1)
            more.setImageResource(R.drawable.more_foreground)
            more.setOnClickListener() {
                showDialogFragment(itemView)
            }
        }
        fun showDialogFragment(view: View) {
            val dialogFragment: DialogFragment = AddTarefaDialog()
            val activity = view.context as AppCompatActivity
            dialogFragment.show(activity.supportFragmentManager, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recyclerview_activity_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarefa = tarefa[position]
        holder.bind(tarefa)
    }

    override fun getItemCount(): Int = tarefa.size
    fun refresh(tarefa: List<Tarefa>) {
        this.tarefa.clear()
        this.tarefa.addAll(tarefa)
        notifyDataSetChanged()
    }

}