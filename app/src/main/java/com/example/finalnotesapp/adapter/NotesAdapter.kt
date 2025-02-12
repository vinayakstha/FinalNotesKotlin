package com.example.finalnotesapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnotesapp.R
import com.example.finalnotesapp.model.NotesModel
import com.example.finalnotesapp.ui.activity.UpdateNotesActivity

class NotesAdapter(
    var context : Context,
    var data : ArrayList<NotesModel>
    ): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
        class NotesViewHolder(itemView: View)
            : RecyclerView.ViewHolder(itemView){
                val update : TextView =itemView.findViewById(R.id.labeledit)
                val noteName : TextView =itemView.findViewById(R.id.displayTask)
                val noteDate : TextView =itemView.findViewById(R.id.date)
                val noteDesc : TextView =itemView.findViewById(R.id.desc)
            }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesAdapter.NotesViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_notes,parent,false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        holder.noteName.text=data[position].notesTitle
        holder.noteDate.text=data[position].notesDate
        holder.noteDesc.text=data[position].notesDescription

        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateNotesActivity::class.java)
            intent.putExtra("notesId",data[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun updateData(notes: List<NotesModel>){
        data.clear()
        data.addAll(notes)
        notifyDataSetChanged()
    }
    fun getNotesId(position: Int): String {
        return data[position].notesId
    }
}