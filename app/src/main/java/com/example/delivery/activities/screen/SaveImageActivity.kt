package com.example.delivery.activities.screen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.delivery.activities.client.home.ClientHomeActivity
import com.example.delivery.databinding.ActivitySaveImageBinding
import com.example.delivery.databinding.ActivitySelectRolesBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class SaveImageActivity : AppCompatActivity() {

    var circleImageView: CircleImageView? = null
    lateinit var binding: ActivitySaveImageBinding

    private var imageFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySaveImageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        circleImageView = binding.imgProfile

        circleImageView?.setOnClickListener{
            selectImage()
        }

        binding.btnNext.setOnClickListener{
            openClientHomeSelected()
        }

        binding.btnConfirm.setOnClickListener{

        }
    }

    private fun openClientHomeSelected() {
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
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