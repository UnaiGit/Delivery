package com.example.delivery.activities.screen

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.VideoView
import com.example.delivery.R
import com.example.delivery.databinding.ActivityHomeBinding
import com.example.delivery.databinding.ActivitySaveImageBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var currentPosition: Int = 0
    private var btnStart: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener{
            goToLogin()
        }
        val uri = Uri.parse("android.resource://"+ packageName +"/" + R.raw.videohome)
        binding.vvHome.setVideoURI(uri)
        binding.vvHome.start()
        binding.vvHome.setOnPreparedListener { mp ->
            mediaPlayer = mp
            mediaPlayer.isLooping = true

            if(currentPosition != 0){
                mediaPlayer.seekTo(currentPosition)
                mediaPlayer.start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        currentPosition = mediaPlayer.currentPosition
        binding.vvHome.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.vvHome.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}