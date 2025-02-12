package com.example.finalnotesapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalnotesapp.R
import com.example.finalnotesapp.databinding.ActivityLoginBinding
import com.example.finalnotesapp.repository.UserRepositoryImpl
import com.example.finalnotesapp.utils.LoadingUtils
import com.example.finalnotesapp.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var loadingUtils: LoadingUtils
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)
        loadingUtils= LoadingUtils(this)
        sharedPreferences=getSharedPreferences("userData", MODE_PRIVATE)
        binding.loginbtn.setOnClickListener{
            val email = binding.loginemail.text.toString()
            val password = binding.loginpassword.text.toString()

            userViewModel.login(email,password) { success, message ->
                if (success) {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                    var intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()

                }
            }


        }

        binding.signuplinkbtn.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}