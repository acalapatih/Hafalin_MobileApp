package com.acalapatih.hafalin.ui.hafalanquran.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.acalapatih.hafalin.BaseActivity
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.Resource
import com.acalapatih.hafalin.core.data.source.local.entity.*
import com.acalapatih.hafalin.core.domain.model.hafalanquran.HafalanAyatModel
import com.acalapatih.hafalin.core.domain.model.hafalanquran.SpeechToTextModel
import com.acalapatih.hafalin.databinding.ActivityHafalanAyatBinding
import com.acalapatih.hafalin.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.hafalin.ui.hafalanquran.viewmodel.HafalanAyatViewModel
import com.acalapatih.hafalin.utils.Const.NOMOR_AYAT
import com.acalapatih.hafalin.utils.Const.NOMOR_SURAT
import com.acalapatih.hafalin.utils.Const.REQUEST_PERMISSION_CODE
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.apache.commons.text.similarity.JaroWinklerSimilarity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class HafalanAyatWithAzure : BaseActivity<ActivityHafalanAyatBinding>() {

    private val viewModel by viewModel<HafalanAyatViewModel>()
    private val nomorSurat by lazy { intent.getStringExtra(NOMOR_SURAT) }
    private val nomorAyat by lazy { intent.getStringExtra(NOMOR_AYAT) }
    private lateinit var audioAyatPlayer: MediaPlayer

    private lateinit var audioFile: File

    private val SAMPLE_RATE = 16000
    private val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
    private val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    private lateinit var audioRecord: AudioRecord
    private var isRecording = false

    override fun getViewBinding(): ActivityHafalanAyatBinding =
        ActivityHafalanAyatBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        nomorSurat?.let { nomorAyat?.let { it1 -> viewModel.getAyat(it, it1) } }

        initView()
    }

    private fun initView() {
        requestPermissions()
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
                    binding.cvHasilHafalan.visibility = View.GONE
                    initObserver()
                    initListener()
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

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initObserver() {
        viewModel.getAyat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getAyat(data)
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
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun getAyat(data: HafalanAyatModel) {
        with(binding) {
            tvSurat.text = data.namaSurat
            tvAyatKe.text = "Ayat ${data.nomorAyat}"
            tvAyat.text = data.lafadzAyat

            btnAudio.setOnClickListener {
                cvAudioAyat.isVisible = true
                tvAudioAyat.text = "${data.namaSurat} : ${data.nomorAyat}"

                audioAyatPlayer = MediaPlayer.create(this@HafalanAyatWithAzure, data.audioAyat.toUri())
                audioAyatPlayer.start()

                btnStop.setOnClickListener {
                    audioAyatPlayer.stop()
                    audioAyatPlayer.release()
                    cvAudioAyat.isVisible = false
                }
            }

            btnRekam.setOnClickListener {
                tvAyat.visibility = View.GONE
                cvHasilHafalan.visibility = View.GONE
//                showDialogRekamSuara(
//                    onClose = { binding.tvAyat.isVisible = true },
//                    onStartRecording = { startRecording() },
//                    onStopRecording = { stopRecording() },
//                    onStopButtonClicked = { speechToTextProcess(
//                        data.namaSurat,
//                        data.nomorAyat,
//                        data.lafadzAyat
//                    ) }
//                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun startRecording() {
        val bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            bufferSize
        )

        audioFile = createAudioFile()

        val audioData = ByteArray(bufferSize)
        val outputStream = FileOutputStream(audioFile)

        audioRecord.startRecording()
        isRecording = true

        Thread {
            while (isRecording) {
                val bytesRead = audioRecord.read(audioData, 0, bufferSize)
                outputStream.write(audioData, 0, bytesRead)
            }

            audioRecord.stop()
            audioRecord.release()
            outputStream.close()

            val fileRequestBody = RequestBody.create("audio/wav".toMediaTypeOrNull(), audioFile)

            viewModel.postSpeechToText(
                fileRequestBody
            )
        }.start()
    }

    private fun createAudioFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        return File.createTempFile(
            "AUDIO_${nomorSurat}_${nomorAyat}",  /* prefix */
            ".wav",         /* suffix */
            storageDir      /* directory */
        )
    }

    @Suppress("DEPRECATION")
    private fun stopRecording() {
        isRecording = false
    }

    private fun speechToTextProcess(namaSurat: String, nomorAyat: String, lafadzAyat: String) {
        binding.tvAyat.isVisible = true
        viewModel.postSpeechToText.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        println("Speech-to-Text Response ====== $data")
                        postSpeechToText(namaSurat, nomorAyat, lafadzAyat, data)
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
    }

    @SuppressLint("SetTextI18n")
    private fun postSpeechToText(namaSurat: String, nomorAyat: String, lafadzAyat: String, data: SpeechToTextModel) {
        with(binding) {
            cvHasilHafalan.visibility = View.VISIBLE
            tvAyatKesalahan.text = data.hasilText

            val jaroWinklerSimilarity = JaroWinklerSimilarity()
            val similarity = jaroWinklerSimilarity.apply(lafadzAyat, data.hasilText)
            println("SIMILARITY === $similarity")

            when(similarity) {
                in 0.71..1.0 ->
                {
                    tvPersentaseKemiripan.text = "${(similarity * 100).toInt()}"
                    tvPredikatHasil.text = "Sangat Baik"
                    tvPredikatHasil.setTextColor(ContextCompat.getColor(this@HafalanAyatWithAzure, R.color.green_9ACD32))
                    tvAyatSelanjutnya.isVisible = true
                    tvLetakKesalahan.isVisible = false
                    tvAyatKesalahan.isVisible = false

                    when (namaSurat) {
                        "Al-Fatihah" -> checkAndInsertAlFatihah(namaSurat, nomorAyat.toInt())
                        "An-Nas" -> checkAndInsertAnNas(namaSurat, nomorAyat.toInt())
                        "Al-Falaq" -> checkAndInsertAlFalaq(namaSurat, nomorAyat.toInt())
                        "Al-Ikhlas" -> checkAndInsertAlIkhlas(namaSurat, nomorAyat.toInt())
                        "At-Takwir" -> checkAndInsertAtTakwir(namaSurat, nomorAyat.toInt())
                        "An-Naba" -> checkAndInsertAnNaba(namaSurat, nomorAyat.toInt())
                        "Al-Mulk" -> checkAndInsertAlMulk(namaSurat, nomorAyat.toInt())
                    }

                    val ayatDihafal = nomorSurat?.let { AyatDihafal(1, it, namaSurat, nomorAyat, ambilWaktuHafalan()) }
                    if (ayatDihafal != null) {
                        viewModel.insertAyatDihafal(ayatDihafal)
                    }
                }
                in 0.41..0.7 -> {
                    tvPersentaseKemiripan.text = "${(similarity * 100).toInt()}"
                    tvPredikatHasil.text = "Cukup Baik"
                    tvPredikatHasil.setTextColor(ContextCompat.getColor(this@HafalanAyatWithAzure, R.color.orange_FF6900))
                    tvLetakKesalahan.visibility = View.VISIBLE
                    tvAyatKesalahan.visibility = View.VISIBLE
                }
                in 0.1..0.4 -> {
                    tvPersentaseKemiripan.text = "${(similarity * 100).toInt()}"
                    tvPredikatHasil.text = "Kurang Baik"
                    tvPredikatHasil.setTextColor(ContextCompat.getColor(this@HafalanAyatWithAzure, R.color.red_F44336))
                    tvLetakKesalahan.visibility = View.VISIBLE
                    tvAyatKesalahan.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkAndInsertAlFatihah(namaSurat: String, nomorAyat: Int) {
        val alFatihah = AlFatihah(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAlFatihah(alFatihah)
    }

    private fun checkAndInsertAnNas(namaSurat: String, nomorAyat: Int) {
        val anNas = AnNas(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAnNas(anNas)
    }

    private fun checkAndInsertAlFalaq(namaSurat: String, nomorAyat: Int) {
        val alFalaq = AlFalaq(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAlFalaq(alFalaq)
    }

    private fun checkAndInsertAlIkhlas(namaSurat: String, nomorAyat: Int) {
        val alIkhlas = AlIkhlas(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAlIkhlas(alIkhlas)
    }

    private fun checkAndInsertAtTakwir(namaSurat: String, nomorAyat: Int) {
        val atTakwir = AtTakwir(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAtTakwir(atTakwir)
    }

    private fun checkAndInsertAnNaba(namaSurat: String, nomorAyat: Int) {
        val anNaba = AnNaba(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAnNaba(anNaba)
    }

    private fun checkAndInsertAlMulk(namaSurat: String, nomorAyat: Int) {
        val alMulk = AlMulk(nomorAyat, namaSurat, nomorAyat, "dihafal")
        viewModel.insertAlMulk(alMulk)
    }

    @SuppressLint("SimpleDateFormat")
    private fun ambilWaktuHafalan(): String {
        val calendar = Calendar.getInstance()

        val waktuFormat = SimpleDateFormat("HH:mm:ss")
        val waktuString = waktuFormat.format(calendar.time)

        val hari = calendar.get(Calendar.DAY_OF_WEEK)

        val hariString = when (hari) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "Unknown"
        }

        val tanggalFormat = SimpleDateFormat("dd/MM/yyyy")
        val tanggalString = tanggalFormat.format(calendar.time)

        return "$waktuString, $hariString, $tanggalString"
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback {
                finish()
            }

            icBookmark.setOnClickListener {
                BookmarkActivity.start(this@HafalanAyatWithAzure)
            }
            tvAyatSelanjutnya.setOnClickListener {
                val nomorAyatSelanjutnya = (nomorAyat?.toInt() ?: 0) + 1
                nomorSurat?.let { nomorSurat ->
                    start(this@HafalanAyatWithAzure,
                        nomorSurat, nomorAyatSelanjutnya.toString())
                }
            }
        }
    }

    companion object {
        fun start(context: Context, nomorSurat: String, nomorAyat: String) {
            val starter = Intent(context, HafalanAyatWithAzure::class.java)
                .putExtra(NOMOR_SURAT, nomorSurat)
                .putExtra(NOMOR_AYAT, nomorAyat)
            context.startActivity(starter)
        }
    }
}