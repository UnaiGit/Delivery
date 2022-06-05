package com.example.delivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.delivery.R
import com.example.delivery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivRegisterIcon.setOnClickListener{
            openRegisterSelected()
        }

        binding.btnLogin.setOnClickListener{
            login()
        }
    }

    private fun login(){
        if (isValidForm(binding.etEmail.toString(),binding.etPassword.toString())){
            Toast.makeText(this, "Hola",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Chao",Toast.LENGTH_SHORT).show()
        }

    }
    private fun isValidForm(email: String, pass: String): Boolean {
        return !(email.isBlank() || pass.isBlank())
    }
    private fun openRegisterSelected(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}