package com.example.finalnotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper      // CHANGED: Import ItemTouchHelper for swipe functionality
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView          // CHANGED: Import RecyclerView (needed for ItemTouchHelper callback)
import com.example.finalnotesapp.adapter.FavouritesAdapter
import com.example.finalnotesapp.utils.FavouritesManager
import com.example.finalnotesapp.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the RecyclerView adapter with the favourites list from FavouritesManager
        adapter = FavouritesAdapter(requireContext(), FavouritesManager.favouritesList)
        binding.favoritesRecyclerView.adapter = adapter
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // CHANGED: Add swipe-to-delete functionality using ItemTouchHelper
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            // We don't support moving items, so return false.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // When swiped, remove the item from the favourites list and update the adapter.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // Remove the item from the in-memory favourites list
                FavouritesManager.favouritesList.removeAt(position)
                // Notify the adapter of item removal
                adapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Removed from favourites", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerView)
    }

    override fun onResume() {
        super.onResume()
        // Refresh adapter in case the favourites list was updated
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
