package com.example.delivery.activities.shop.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.delivery.R
import com.example.delivery.activities.screen.LoginActivity
import com.example.delivery.databinding.ActivityLoginBinding
import com.example.delivery.databinding.ActivityShopHomeBinding
import com.example.delivery.fragments.shop.*
import com.example.delivery.model.createUser.User
import com.example.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlin.math.log

class ShopHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopHomeBinding
    private val TAG = "ShopHomeActivity"
    var sharedPref: SharedPref? = null
    var bottomNavigation : BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShopHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        setContentView(binding.root)
        openFragment(ShopPurchasesFragment())
        getUserFromPreference()
        bottomNavigation = binding.bottomNavigation
        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_purchases ->{
                    openFragment(ShopPurchasesFragment())
                    true
                }
                R.id.item_category ->{
                    openFragment(ShopCategoryFragment())
                    true
                }
                R.id.item_items ->{
                    openFragment(ShopItemsFragment())
                    true
                }
                R.id.item_profile ->{
                    openFragment(ShopProfileFragment())
                    true
                }
                else -> false
            }
        }
        binding.btnLogOut.setOnClickListener{
            logout()
        }
    }

    private fun openFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainerShop, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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