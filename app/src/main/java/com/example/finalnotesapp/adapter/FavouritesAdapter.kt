package com.example.finalnotesapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnotesapp.R
import com.example.finalnotesapp.model.NotesModel

class FavouritesAdapter(
    var context: Context,
    var data: MutableList<NotesModel>
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteName: TextView = itemView.findViewById(R.id.displayTask)
        val noteDate: TextView = itemView.findViewById(R.id.date)
        val noteDesc: TextView = itemView.findViewById(R.id.desc)
        // CHANGED: We are hiding update and favourite buttons in the favourites list.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_notes, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val note = data[position]
        holder.noteName.text = note.notesTitle
        holder.noteDate.text = note.notesDate
        holder.noteDesc.text = note.notesDescription

        // CHANGED: Hide update and favourite buttons for the favourites view.
        holder.itemView.findViewById<View>(R.id.labeledit)?.visibility = View.GONE
        holder.itemView.findViewById<View>(R.id.favoriteButton)?.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
