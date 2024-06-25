package com.acalapatih.oneayat.ui.hafalanquran.activity

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanQuranModel
import com.acalapatih.oneayat.core.factory.SettingViewModelFactory
import com.acalapatih.oneayat.core.preference.SettingPreferences
import com.acalapatih.oneayat.databinding.ActivityHafalanQuranBinding
import com.acalapatih.oneayat.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.oneayat.utils.NotificationReceiver
import com.acalapatih.oneayat.ui.hafalanquran.adapter.HafalanQuranAdapter
import com.acalapatih.oneayat.ui.hafalanquran.viewmodel.HafalanQuranViewModel
import com.acalapatih.oneayat.ui.setting.SettingViewModel
import com.acalapatih.oneayat.utils.Const.REQUEST_PERMISSION_CODE
import com.acalapatih.oneayat.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HafalanQuranActivity : BaseActivity<ActivityHafalanQuranBinding>(), HafalanQuranAdapter.OnUserClickListener {

    private val viewModel by viewModel<HafalanQuranViewModel>()
    private lateinit var listSuratAdapter: HafalanQuranAdapter
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
    private var lastNotificationTime: Long = 0

    override fun getViewBinding(): ActivityHafalanQuranBinding =
        ActivityHafalanQuranBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        viewModel.getListSurah()

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this@HafalanQuranActivity)
        val itemDecoration = DividerItemDecoration(this@HafalanQuranActivity, layoutManager.orientation)
        with(binding.rvHafalanSurat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initObserver() {
        viewModel.getListSurah.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getListSurah(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        showToast(
                            it, Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }

        viewModel.ayatDihafal.observe(this) { ayatDihafal ->
            if (ayatDihafal == null) {
                requestNotificationPermission()
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun getListSurah(data: HafalanQuranModel) {
        listSuratAdapter = HafalanQuranAdapter(data.listSurat, this)
        binding.rvHafalanSurat.adapter = listSuratAdapter
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback {
                finish()
            }

            icBookmark.setOnClickListener {
                BookmarkActivity.start(this@HafalanQuranActivity)
            }
            icNotifikasiPengingat.setOnClickListener {
                requestNotificationPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    showDialogAturNotifikasi { hour, minute ->
                        notificationChannel()
                        scheduleNotification(hour, minute)
                    }
                } else {
                    showToast(
                        this.getString(R.string.toast_permission),
                        Toast.LENGTH_SHORT
                    )
                    onBackPressedDispatcher.onBackPressed()
                    onBackPressedDispatcher.addCallback {
                        finish()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // If the specified time has passed, schedule the notification for the next day
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Calculate the time difference between now and the next notification time
        val triggerAtMillis = calendar.timeInMillis - System.currentTimeMillis()

        // Schedule a repeating alarm for every 1 hour starting from the specified time
        val intervalMillis = 24 * 60 * 60 * 1000L // 1 hour in milliseconds

        // Calculate the next notification time based on the last notification time
        val nextNotifyTime = if (lastNotificationTime == 0L) {
            System.currentTimeMillis() + triggerAtMillis
        } else {
            lastNotificationTime + intervalMillis
        }
        lastNotificationTime = nextNotifyTime

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(this, NotificationReceiver::class.java), PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                lastNotificationTime,
                intervalMillis,
                pendingIntent
            )
        } else {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                lastNotificationTime,
                intervalMillis,
                pendingIntent
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun rescheduleNotification(hour: Int, minute: Int) {
        // Cancel the previous alarm
        alarmManager.cancel(pendingIntent)

        // Reschedule the notification with the new time
        scheduleNotification(hour, minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationChannel() {
        val name = "Sudahkah Kamu Setor Hafalan Hari Ini?"
        val descriptionText = "Lanjutkan hafalan Al-Qur'an mu minimal satu hari satu ayat, Hamasahh!!"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("Notification", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onUserClicked(nomorSurat: String, clicked: String) {
        HafalanSuratActivity.start(this@HafalanQuranActivity, nomorSurat)
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, HafalanQuranActivity::class.java)
            context.startActivity(starter)
        }
    }
}