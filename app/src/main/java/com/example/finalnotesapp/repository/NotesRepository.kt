package com.example.finalnotesapp.repository

import com.example.finalnotesapp.model.NotesModel

interface NotesRepository {
    fun addNotes(
        notes: NotesModel,
        callback: (Boolean, String) -> Unit
    )
    fun updateNotes(
        notesId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )
    fun deleteNotes(
        notesId: String,
        callback: (Boolean, String) -> Unit
    )
    fun getNotesById(
        notesId: String,
        callback: (NotesModel?, Boolean, String) -> Unit
    )
    fun getAllNotes(
        callback:(List<NotesModel>?, Boolean, String) -> Unit
    )
}