package com.example.finalnotesapp.repository

import com.example.finalnotesapp.model.FavouritesModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouritesRepositoryImpl : FavouritesRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref: DatabaseReference = database.reference.child("favourites")

    override fun addFavourite(
        favourite: FavouritesModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        favourite.favouriteId = id
        ref.child(id).setValue(favourite).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Favourite Added Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateFavourite(
        favouriteId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(favouriteId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Favourite Updated Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteFavourite(
        favouriteId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(favouriteId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Favourite Deleted Successfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getFavouriteById(
        favouriteId: String,
        callback: (FavouritesModel?, Boolean, String) -> Unit
    ) {
        ref.child(favouriteId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val model = snapshot.getValue(FavouritesModel::class.java)
                    callback(model, true, "Favourite Fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllFavourites(callback: (List<FavouritesModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favourites = mutableListOf<FavouritesModel>()
                if (snapshot.exists()) {
                    for (eachFavourite in snapshot.children) {
                        val data = eachFavourite.getValue(FavouritesModel::class.java)
                        if (data != null) {
                            favourites.add(data)
                        }
                    }
                    callback(favourites, true, "Favourites Fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }
}
