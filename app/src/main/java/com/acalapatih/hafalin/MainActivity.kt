package com.acalapatih.hafalin

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.acalapatih.hafalin.utils.Const
import com.acalapatih.hafalin.databinding.ActivityMainBinding
import com.acalapatih.hafalin.ui.home.activity.HomeActivity
import com.acalapatih.hafalin.ui.onboarding.OnboardingActivity
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

        val transitionAnimation =
            AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation)
        binding.imgHafalin.animation = transitionAnimation

        activityScope.launch {
            delay(Const.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                val sharedPreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
                val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

                if (isFirstRun) {
                    // Set the flag to false after the first run
                    sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
                    OnboardingActivity.start(this@MainActivity)
                } else {
                    HomeActivity.start(this@MainActivity, "home")
                }
                finish()
            }
        }
    }
}