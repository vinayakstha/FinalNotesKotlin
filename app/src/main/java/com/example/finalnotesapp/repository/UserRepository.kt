package com.example.finalnotesapp.repository

import com.example.finalnotesapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {



    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)

    fun signup(email: String, password: String, callback: (Boolean, String,String) -> Unit)

    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit)

    fun forgotPassword(email: String, callback: (Boolean, String) -> Unit)

    fun getCurrentUser(): FirebaseUser?

    fun getUserData(userId: String, callback: (UserModel?) -> Unit)

    fun updateUserData(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit)

    fun updateUserEmail(newEmail: String, callback: (Boolean) -> Unit)
}