package com.example.finalnotesapp.repository

import com.example.finalnotesapp.model.NotesModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotesRepositoryImpl: NotesRepository  {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference = database.reference.child("notes")

    override fun addNotes(
        notes: NotesModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = ref.push().key.toString()
        notes.notesId = id
        ref.child(id).setValue(notes).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Notes Added Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }



    }

    override fun updateNotes(
        notesId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(notesId).updateChildren(data).addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Notes Updated Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteNotes(notesId: String, callback: (Boolean, String) -> Unit) {
        ref.child(notesId).removeValue().addOnCompleteListener(){
            if(it.isSuccessful){
                callback(true, "Notes Deleted Successfully")
            }else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getNotesById(notesId: String, callback: (NotesModel?, Boolean, String) -> Unit) {
        ref.child(notesId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model = snapshot.getValue(NotesModel::class.java)
                    callback(model, true, "Notes Fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }


        })

    }

    override fun getAllNotes(callback: (List<NotesModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var notes = mutableListOf<NotesModel>()
                if (snapshot.exists()) {
                    for (eachNotes in snapshot.children) {
                        val data = eachNotes.getValue(NotesModel::class.java)
                        if (data != null) {
                            notes.add(data)
                        }
                    }
                    callback(notes, true, "Notes Fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }
}