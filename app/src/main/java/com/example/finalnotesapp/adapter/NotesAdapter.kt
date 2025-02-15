package com.example.finalnotesapp.adapter

//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.example.finalnotesapp.R
//import com.example.finalnotesapp.model.NotesModel
//import com.example.finalnotesapp.ui.activity.UpdateNotesActivity
//import com.example.finalnotesapp.viewmodel.NotesViewModel
//
//class NotesAdapter(
//    var context: Context,
//    var data: ArrayList<NotesModel>
//
//) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
//
//    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val update: TextView = itemView.findViewById(R.id.labeledit)
//        val noteName: TextView = itemView.findViewById(R.id.displayTask)
//        val noteDate: TextView = itemView.findViewById(R.id.date)
//        val noteDesc: TextView = itemView.findViewById(R.id.desc)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
//        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_notes, parent, false)
//        return NotesViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
//        holder.noteName.text = data[position].notesTitle
//        holder.noteDate.text = data[position].notesDate
//        holder.noteDesc.text = data[position].notesDescription
//
//        holder.update.setOnClickListener {
//            val intent = Intent(context, UpdateNotesActivity::class.java)
//            intent.putExtra("notesId", data[position].notesId) // Pass only the notesId
//            context.startActivity(intent)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    fun updateData(notes: List<NotesModel>) {
//        data.clear()
//        data.addAll(notes)
//        notifyDataSetChanged()
//    }
//
//    fun getNotesId(position: Int): String {
//        return data[position].notesId
//    }
//}

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnotesapp.R
import com.example.finalnotesapp.model.NotesModel
import com.example.finalnotesapp.ui.activity.UpdateNotesActivity
import com.example.finalnotesapp.utils.FavouritesManager  // CHANGED: Import FavouritesManager

class NotesAdapter(
    var context: Context,
    var data: ArrayList<NotesModel>
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val update: TextView = itemView.findViewById(R.id.labeledit)
        val noteName: TextView = itemView.findViewById(R.id.displayTask)
        val noteDate: TextView = itemView.findViewById(R.id.date)
        val noteDesc: TextView = itemView.findViewById(R.id.desc)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)  // CHANGED: Reference to favourite button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_notes, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = data[position]
        holder.noteName.text = note.notesTitle
        holder.noteDate.text = note.notesDate
        holder.noteDesc.text = note.notesDescription

        // Existing update functionality remains unchanged
        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateNotesActivity::class.java)
            intent.putExtra("notesId", note.notesId)
            context.startActivity(intent)
        }

        // CHANGED: Set onClickListener for the favourite button.
        holder.favoriteButton.setOnClickListener {
            // Check if the note is already in favourites
            if (!FavouritesManager.favouritesList.contains(note)) {
                FavouritesManager.favouritesList.add(note)
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Already in Favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(notes: List<NotesModel>) {
        data.clear()
        data.addAll(notes)
        notifyDataSetChanged()
    }

    fun getNotesId(position: Int): String {
        return data[position].notesId
    }
}



