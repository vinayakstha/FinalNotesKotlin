package com.example.finalnotesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnotesapp.R
import com.example.finalnotesapp.adapter.NotesAdapter
import com.example.finalnotesapp.databinding.FragmentHomeBinding
import com.example.finalnotesapp.repository.NotesRepositoryImpl
import com.example.finalnotesapp.ui.activity.AddNotesActivity
import com.example.finalnotesapp.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var notesViewModel: NotesViewModel
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = NotesRepositoryImpl()
        notesViewModel= NotesViewModel(repo)

        adapter = NotesAdapter(requireContext(), ArrayList())

        binding.notesrecyclerView.adapter=adapter
        binding.notesrecyclerView.layoutManager= LinearLayoutManager(requireContext())

        notesViewModel.loading.observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.progressBar.visibility=View.VISIBLE
            }else{
                binding.progressBar.visibility=View.GONE

            }

        }
        notesViewModel.getAllNotes()
        notesViewModel.allnotes.observe(viewLifecycleOwner){it ->
            it?.let {
                adapter.updateData(it)
            }
        }
        binding.floatingActionButton.setOnClickListener(){
            val intent = Intent(requireContext(), AddNotesActivity::class.java)
            startActivity(intent)

        }
        ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val noteId = adapter.getNotesId(viewHolder.adapterPosition)
                notesViewModel.deleteNotes(noteId){
                    success, message ->
                    if(success){
                       Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }).attachToRecyclerView(binding.notesrecyclerView)

    }


}
