package com.acalapatih.oneayat

import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.acalapatih.oneayat.utils.Const
import com.acalapatih.oneayat.databinding.ActivityMainBinding
import com.acalapatih.oneayat.ui.home.activity.HomeActivity
import com.acalapatih.oneayat.ui.onboarding.OnboardingActivity
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
        binding.imgOneAyat.animation = transitionAnimation

        activityScope.launch {
            delay(Const.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                OnboardingActivity.start(this@MainActivity)
                finish()
            }
        }
    }
}