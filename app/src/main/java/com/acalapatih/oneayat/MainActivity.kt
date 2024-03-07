package com.acalapatih.oneayat

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.acalapatih.oneayat.utils.Const
import com.acalapatih.oneayat.databinding.ActivityMainBinding
import com.acalapatih.oneayat.ui.home.activity.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activityScope.launch {
            delay(Const.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                HomeActivity.start(this@MainActivity, "home")
                finish()
            }
        }
    }
}