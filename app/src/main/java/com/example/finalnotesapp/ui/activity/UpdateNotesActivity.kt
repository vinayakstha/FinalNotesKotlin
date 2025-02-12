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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = NotesRepositoryImpl()
        notesViewModel = NotesViewModel(repo)

        var notesId = intent.getStringExtra("notesId").toString()

        notesViewModel.getNotesById(notesId)

        notesViewModel.notes.observe(this){
            binding.notesupdateEditText.setText(it.notesTitle)
            binding.notesupdateDescriptionEditText.setText(it.notesDescription)
            binding.selectDateButton.setText(it.notesDate)


        }
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        binding.selectDateButton.setOnClickListener(){
            val datePickerDialog= DatePickerDialog(
                this@UpdateNotesActivity,
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.selectDateButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day

            )
            datePickerDialog.show()
        }
        binding.UpdateNoteButton.setOnClickListener(){
            var title = binding.notesupdateEditText.text.toString()
            var description = binding.notesupdateDescriptionEditText.text.toString()
            var date = binding.selectDateButton.text.toString()

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["notesTitle"] = title
            updatedMap["notesDescription"] = description
            updatedMap["notesDate"] = date

            notesViewModel.updateNotes(notesId, updatedMap){
                success, message ->
                if(success){
                    Toast.makeText(this@UpdateNotesActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateNotesActivity, message, Toast.LENGTH_SHORT).show()
                }
            }



        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}