package com.example.finalnotesapp.repository

import com.example.finalnotesapp.model.FavouritesModel

interface FavouritesRepository {
    fun addFavourite(
        favourite: FavouritesModel,
        callback: (Boolean, String) -> Unit
    )
    fun updateFavourite(
        favouriteId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    )
    fun deleteFavourite(
        favouriteId: String,
        callback: (Boolean, String) -> Unit
    )
    fun getFavouriteById(
        favouriteId: String,
        callback: (FavouritesModel?, Boolean, String) -> Unit
    )
    fun getAllFavourites(
        callback: (List<FavouritesModel>?, Boolean, String) -> Unit
    )
}
