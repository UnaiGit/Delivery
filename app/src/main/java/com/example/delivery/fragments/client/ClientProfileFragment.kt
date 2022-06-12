package com.example.delivery.fragments.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.delivery.R
import com.example.delivery.activities.screen.LoginActivity
import com.example.delivery.activities.screen.SelectRolesActivity
import com.example.delivery.model.createUser.User
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView

class ClientProfileFragment : Fragment() {

    var myView: View? = null
    var buttonSelectRol : Button? = null
    var buttonUpdateProfile : Button? = null
    var circleImageUser : CircleImageView? = null
    var textViewName : TextView? = null
    var textViewEmail : TextView? = null
    var textViewPhone : TextView? = null
    var sharedPref : SharedPref? = null
    var user : User? = null
    var imageViewLogout : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_client_profile, container, false)

        sharedPref = SharedPref(requireActivity())

        buttonUpdateProfile = myView?.findViewById(R.id.btnEditProfile)
        buttonSelectRol = myView?.findViewById(R.id.btnSelectRol)
        textViewName = myView?.findViewById(R.id.tvProfileName)
        textViewEmail = myView?.findViewById(R.id.tvProfileEmail)
        textViewPhone = myView?.findViewById(R.id.tvProfileMobile)
        circleImageUser = myView?.findViewById(R.id.profileImagePicker)
        imageViewLogout = myView?.findViewById(R.id.ivCloseSession)

        buttonSelectRol?.setOnClickListener{
            openSelectRoles()
        }

        imageViewLogout?.setOnClickListener{
            logout()
        }

        getUserFromPreference()

        textViewName?.text = "${user?.name} ${user?.lastname}"
        textViewEmail?.text = user?.email
        textViewPhone?.text = user?.phone

        if(!user?.image.isNullOrBlank()){
            Glide.with(requireContext()).load(user?.image).into(circleImageUser!!)
        }

        return myView
    }

    private fun logout(){
        sharedPref?.remove("user")
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
    private fun getUserFromPreference(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private fun openSelectRoles() {
        val i = Intent(requireContext(), SelectRolesActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

}