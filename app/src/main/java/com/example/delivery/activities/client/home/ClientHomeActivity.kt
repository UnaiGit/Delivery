package com.example.delivery.activities.client.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.delivery.activities.screen.LoginActivity
import com.example.delivery.databinding.ActivityClientHomeBinding
import com.example.delivery.databinding.ActivityLoginBinding
import com.example.delivery.model.createUser.User
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson
import kotlin.math.log

class ClientHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientHomeBinding
    private val TAG = "ClientHomeActivity"
    var sharedPref: SharedPref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        setContentView(binding.root)
        getUserFromPreference()

        binding.btnLogOut.setOnClickListener{
            logout()
        }
    }

    private fun logout(){
        sharedPref?.remove("user")
        startActivity(Intent(this, LoginActivity::class.java))
    }
    private fun getUserFromPreference(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "User: ${user}")
        }
    }
}