package com.example.delivery.activities.client.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.delivery.R
import com.example.delivery.activities.screen.LoginActivity
import com.example.delivery.databinding.ActivityClientHomeBinding
import com.example.delivery.databinding.ActivityLoginBinding
import com.example.delivery.fragments.client.ClientCategoriesFragment
import com.example.delivery.fragments.client.ClientProfileFragment
import com.example.delivery.fragments.client.ClientPurchasesFragment
import com.example.delivery.model.createUser.User
import com.example.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlin.math.log

class ClientHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientHomeBinding
    private val TAG = "ClientHomeActivity"
    var sharedPref: SharedPref? = null
    var bottomNavigation : BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        setContentView(binding.root)
        openFragment(ClientCategoriesFragment())
        getUserFromPreference()
        bottomNavigation = binding.bottomNavigation
        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_home ->{
                    openFragment(ClientCategoriesFragment())
                     true
                }
                R.id.item_purchases ->{
                    openFragment(ClientPurchasesFragment())
                    true
                }
                R.id.item_profile ->{
                    openFragment(ClientProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment : Fragment){

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.flContainer.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserFromPreference(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "User: ${user}")
        }
    }
}