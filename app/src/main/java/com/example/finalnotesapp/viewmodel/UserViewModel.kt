package com.example.finalnotesapp.viewmodel

import com.example.finalnotesapp.model.UserModel
import com.example.finalnotesapp.repository.UserRepository

class UserViewModel (val userRepository: UserRepository){
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit){
        userRepository.login(email,password,callback)
    }
    fun signup(email: String, password: String, callback: (Boolean, String,String) -> Unit){
        userRepository.signup(email,password,callback)
    }
    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit){
        userRepository.addUserToDatabase(userId, userModel,callback)
    }
    fun forgotPassword(email: String, password: String, callback: (Boolean, String) -> Unit){
        userRepository.forgotPassword(email,callback)
    }
    fun getCurrentUser(email: String, password: String, callback: (Boolean, String) -> Unit){
        userRepository.getCurrentUser()
    }
}