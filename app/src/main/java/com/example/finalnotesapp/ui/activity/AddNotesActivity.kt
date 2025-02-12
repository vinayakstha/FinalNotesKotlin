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
import com.example.finalnotesapp.databinding.ActivityAddNotesBinding
import com.example.finalnotesapp.model.NotesModel
import com.example.finalnotesapp.repository.NotesRepositoryImpl
import com.example.finalnotesapp.utils.LoadingUtils
import com.example.finalnotesapp.viewmodel.NotesViewModel

class AddNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNotesBinding
    lateinit var notesViewModel: NotesViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNotesBinding.inflate(layoutInflater)

        setContentView(binding.root)
        loadingUtils = LoadingUtils(this)

        val notesRepository = NotesRepositoryImpl()
        notesViewModel = NotesViewModel(notesRepository)

        binding.addNoteButton.setOnClickListener(){
            val title = binding.notesEditText.text.toString()
            val description = binding.notesDescriptionEditText.text.toString()
            val date = binding.selectDateButton.text.toString()

            if(title.isEmpty() || description.isEmpty() || date.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val model = NotesModel("",title, description, date)

            notesViewModel.addNotes(model){
                success, message ->
                if(success){
                    Toast.makeText(this@AddNotesActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@AddNotesActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        binding.selectDateButton.setOnClickListener(){
            val datePickerDialog= DatePickerDialog(
                this@AddNotesActivity,
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.selectDateButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day

            )
            datePickerDialog.show()
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}