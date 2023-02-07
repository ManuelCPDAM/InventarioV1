package com.example.inventariov1_10_1_2023.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.inventariov1_10_1_2023.activitys.MainActivity
import com.example.inventariov1_10_1_2023.databinding.ActivitySplashBinding

class splashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * Imagen splash
         */
        screenSplash.setKeepOnScreenCondition{true}
        Thread.sleep(3000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}