package com.example.finalnotesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.finalnotesapp.model.NotesModel
import com.example.finalnotesapp.repository.NotesRepository

class NotesViewModel( val repository: NotesRepository) {
    fun addNotes(notesModel: NotesModel, callback:(Boolean, String)-> Unit){
        repository.addNotes(notesModel, callback);

    }
    fun updateNotes(notesId: String, data: MutableMap<String, Any>, callback:(Boolean, String)-> Unit){
        repository.updateNotes(notesId, data, callback);
    }
    fun deleteNotes(notesId: String, callback:(Boolean, String)-> Unit){
        repository.deleteNotes(notesId, callback);

    }
    var _notes= MutableLiveData<NotesModel>()
    var notes= MutableLiveData<NotesModel>()
        get()=_notes

    var _allnotes = MutableLiveData<List<NotesModel>>()
    var allnotes = MutableLiveData<List<NotesModel>>()
        get()=_allnotes

    fun getNotesById(notesId: String){
        repository.getNotesById(notesId){
            notes, success, message ->
            if(success){
                _notes.value=notes
            }
        }
    }
    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get()=_loading

    fun getAllNotes(){
        repository.getAllNotes{
            notes, success, message ->
            if(success){
                _allnotes.value=notes
                _loading.value=false
            }
            }
    }



}





