package com.example.mymusic

import InternetReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mymusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private var internetReceiver: InternetReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        internetReceiver = InternetReceiver()
        binding.songPause.setOnClickListener {
            ContextCompat.startForegroundService(this,
            Intent(this,MusicService::class.java).apply{
                putExtra(MusicService.ACTION,Constants.START)
            })
        }
    }
    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(internetReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetReceiver)
    }
}
