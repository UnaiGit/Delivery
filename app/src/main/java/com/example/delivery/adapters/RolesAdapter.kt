package com.example.delivery.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivery.R
import com.example.delivery.activities.client.home.ClientHomeActivity
import com.example.delivery.activities.delivery.home.DeliveryHomeActivity
import com.example.delivery.activities.screen.SelectRolesActivity
import com.example.delivery.activities.shop.home.ShopHomeActivity
import com.example.delivery.model.createUser.Rol
import com.example.delivery.utils.SharedPref

class RolesAdapter(val context: Activity, val roles: ArrayList<Rol>) :
    RecyclerView.Adapter<RolesAdapter.RolesViewHolder>() {
    val sharedPref = SharedPref(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_roles, parent, false)
        return RolesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {
        val rol = roles[position]
        holder.tvRol.text = rol.name
        Glide.with(context).load(rol.image).into(holder.imgView)

        holder.itemView.setOnClickListener{ goToRol(rol) }
    }

    private fun goToRol(rol : Rol){
        if(rol.name == "SHOP"){
            sharedPref.save("rol","SHOP")
            val i = Intent(context,ShopHomeActivity::class.java)
            context.startActivity(i)
        }
        else if(rol.name == "CLIENTE"){
            sharedPref.save("rol","CLIENTE")
            val i = Intent(context,ClientHomeActivity::class.java)
            context.startActivity(i)
        }
        else if(rol.name == "DELIVERY"){
            sharedPref.save("rol","DELIVERY")
            val i = Intent(context,DeliveryHomeActivity::class.java)
            context.startActivity(i)
        }
    }

    class RolesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRol: TextView
        val imgView: ImageView

        init {
            tvRol = view.findViewById(R.id.tvRol)
            imgView = view.findViewById(R.id.imgRol)
        }
    }
}