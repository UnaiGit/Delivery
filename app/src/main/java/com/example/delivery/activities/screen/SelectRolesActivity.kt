 package com.example.delivery.activities.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.adapters.RolesAdapter
import com.example.delivery.databinding.ActivityClientHomeBinding
import com.example.delivery.databinding.ActivitySelectRolesBinding
import com.example.delivery.model.createUser.User
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

 class SelectRolesActivity : AppCompatActivity() {
     private lateinit var binding: ActivitySelectRolesBinding
     private var recyclerViewRoles: RecyclerView? = null
     private var user: User? = null
     var adapter: RolesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectRolesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerViewRoles = binding.rvRoles
        recyclerViewRoles?.layoutManager = LinearLayoutManager(this)
        getUserFromPreference()
        adapter = RolesAdapter(this, user?.roles!!)
        recyclerViewRoles?.adapter = adapter
    }

     private fun getUserFromPreference(){
         val sharedPref = SharedPref(this)
         val gson = Gson()
         if (!sharedPref.getData("user").isNullOrBlank()){
             user = gson.fromJson(sharedPref.getData("user"), User::class.java)
         }
     }
}