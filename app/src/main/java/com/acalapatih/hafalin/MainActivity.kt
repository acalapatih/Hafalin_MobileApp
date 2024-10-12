package com.acalapatih.hafalin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.acalapatih.hafalin.ui.home.activity.HomeActivity
import com.acalapatih.hafalin.ui.onboarding.OnboardingActivity
import com.acalapatih.hafalin.utils.Const
import com.acalapatih.hafalin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

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

            val sharedPreferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE)
            val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

            val intent: Intent = if (isFirstRun) {
                sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
                Intent(this@MainActivity, OnboardingActivity::class.java)
            } else {
                Intent(this@MainActivity, HomeActivity::class.java).apply {
                    putExtra("fragment", "home") // Assuming 'home' is a fragment tag
                }
            }

            startActivity(intent)
            finish()

//            splashScreen.setKeepVisibleCondition { it }
        }
    }
}