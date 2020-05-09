package com.utn.haveiseenit.activities.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.utn.haveiseenit.R
import com.utn.haveiseenit.entities.Note

class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_item, parent, false) as View
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.note_type_text).text = "GENERAL"
        holder.view.findViewById<TextInputEditText>(R.id.note_text).setText(notes[position].text)
    }

    override fun getItemCount(): Int = notes.size
}