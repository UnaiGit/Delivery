package com.example.delivery.activities.screen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.delivery.activities.client.home.ClientHomeActivity
import com.example.delivery.databinding.ActivitySaveImageBinding
import com.example.delivery.databinding.ActivitySelectRolesBinding
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.model.createUser.User
import com.example.delivery.provider.UsersProvider
import com.example.delivery.utils.SharedPref
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SaveImageActivity : AppCompatActivity() {

    var circleImageView: CircleImageView? = null
    lateinit var binding: ActivitySaveImageBinding
    private var imageFile: File? = null
    var usersProvider = UsersProvider()
    var user: User? = null
    val TAG = "SaveImageActivity"
    var sharedPref : SharedPref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySaveImageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        circleImageView = binding.imgProfile

        getUserFromPreference()

        circleImageView?.setOnClickListener{
            selectImage()
        }

        binding.btnNext.setOnClickListener{
            openClientHomeSelected()
        }

        binding.btnConfirm.setOnClickListener{
            saveImage()
        }
    }

    private fun saveImage(){
        if(imageFile != null && user != null){
            usersProvider.update(imageFile!!, user!!)?.enqueue(object : Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "RESPONSE: $response")
                    Log.d(TAG, "BODY: ${response.body()}")
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    Toast.makeText(this@SaveImageActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        else {
            Toast.makeText(this, "The image cannot be null neither the session users", Toast.LENGTH_LONG).show()
        }

    }

    private fun openClientHomeSelected() {
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun getUserFromPreference(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK){
                val fileUri = data?.data
                imageFile = File(fileUri?.path)
                circleImageView?.setImageURI(fileUri)
            }
            else if (resultCode == ImagePicker.RESULT_ERROR){
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show()
            }
    }
    private fun selectImage(){
        ImagePicker.with(this)
            .crop()
            .compress(maxSize = 1024)
            .maxResultSize(1080,1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }
}