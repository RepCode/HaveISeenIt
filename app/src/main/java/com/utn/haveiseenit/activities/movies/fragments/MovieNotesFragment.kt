package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.adapters.NotesAdapter
import com.utn.haveiseenit.activities.movies.viewModels.MovieDetailViewModel
import com.utn.haveiseenit.entities.Note
import com.utn.haveiseenit.entities.NoteTag

class MovieNotesFragment : Fragment() {
    private lateinit var v: View
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.onCommitChanges().observe(requireActivity(), Observer<Unit>
        {
            if (!v.findViewById<TextInputEditText>(R.id.note_text).text.isNullOrBlank()) {
                movieDetailViewModel.addNote(
                    v.findViewById<TextInputEditText>(R.id.note_text).text.toString(),
                    NoteTag.general
                )
                v.findViewById<TextInputEditText>(R.id.note_text).setText("")
                movieDetailViewModel.clearEditMode()
            }
        })
        movieDetailViewModel.getMovieNotes()
            .observe(requireActivity(), Observer<List<Note>> { notes ->
                val viewManager = LinearLayoutManager(context)
                val notesAdapter = NotesAdapter(notes)
                v.findViewById<RecyclerView>(R.id.notes_recycler_view).apply {
                    layoutManager = viewManager
                    adapter = notesAdapter
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_movie_notes, container, false)
        v.findViewById<TextInputEditText>(R.id.note_text).addTextChangedListener {
            if(v.findViewById<TextInputEditText>(R.id.note_text).isFocused){
                movieDetailViewModel.setEditMode()
            }
        }
        return v
    }
}
