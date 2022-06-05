package com.example.delivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.delivery.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivLoginIcon.setOnClickListener{
            onLoginSelected()
        }

        binding.btnRegister.setOnClickListener{
            onRegisterSelected()
        }
    }

    private fun onRegisterSelected(){
            if (isValidForm(binding.etName.toString(),binding.etSurname.toString(),binding.etEmail.toString(),binding.etMobile.toString(),binding.etPassword.toString(),binding.etPasswordConfirm.toString())){
            Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Chao", Toast.LENGTH_SHORT).show()
        }

    }
    private fun isValidForm(name: String, surname: String, email: String, mobile: String, pass:String, passRep: String): Boolean {
        if (name.isBlank()){
            return false
        }
        if (surname.isBlank()){
            return false
        }
        if (mobile.isBlank()){
            return false
        }
        if (pass.isBlank()){
            return false
        }
        if (passRep.isBlank()){
            return false
        }
        return true
    }

    private fun onLoginSelected() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}