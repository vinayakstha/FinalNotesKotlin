package com.example.finalnotesapp.ui.activity

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalnotesapp.R
import com.example.finalnotesapp.databinding.ActivityUpdateNotesBinding
import com.example.finalnotesapp.repository.NotesRepositoryImpl
import com.example.finalnotesapp.utils.LoadingUtils
import com.example.finalnotesapp.viewmodel.NotesViewModel

class UpdateNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateNotesBinding
    lateinit var notesViewModel: NotesViewModel
    private var notesId: String = "" // Ensure notesId is initialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel and Repository
        val repo = NotesRepositoryImpl()
        notesViewModel = NotesViewModel(repo)

        // Get notesId from the intent
        notesId = intent.getStringExtra("notesId").toString()

        // Fetch the note by ID
        notesViewModel.getNotesById(notesId)

        // Observe the note data and update UI
        notesViewModel.notes.observe(this) { note ->
            if (note != null) {
                // Update the notesId with the correct ID from the fetched note
                notesId = note.notesId

                // Populate UI fields with the note data
                binding.notesupdateEditText.setText(note.notesTitle)
                binding.notesupdateDescriptionEditText.setText(note.notesDescription)
                binding.datePickerButton.text = note.notesDate
            }
        }

        // Date Picker Logic
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@UpdateNotesActivity,
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.datePickerButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Update Note Button Logic
        binding.UpdateNoteButton.setOnClickListener {
            val title = binding.notesupdateEditText.text.toString()
            val description = binding.notesupdateDescriptionEditText.text.toString()
            val date = binding.datePickerButton.text.toString()

            // Validate notesId before proceeding
            if (notesId.isEmpty()) {
                Toast.makeText(this, "Invalid note ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a map of updated fields
            val updatedMap = mutableMapOf<String, Any>(
                "notesTitle" to title,
                "notesDescription" to description,
                "notesDate" to date
            )

            // Call the update method
            notesViewModel.updateNotes(notesId, updatedMap) { success, message ->
                if (success) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}