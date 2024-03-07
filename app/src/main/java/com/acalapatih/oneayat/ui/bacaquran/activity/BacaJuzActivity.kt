package com.acalapatih.oneayat.ui.bacaquran.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.databinding.ActivityBacaJuzBinding
import com.acalapatih.oneayat.ui.bookmark.activity.BookmarkActivity
import com.acalapatih.oneayat.ui.hafalanquran.activity.HafalanQuranActivity
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.oneayat.ui.bacaquran.adapter.BacaJuzAdapter
import com.acalapatih.oneayat.ui.bacaquran.adapter.BacaSuratAdapter
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.BacaJuzViewModel
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.ListJuzViewModel
import com.acalapatih.oneayat.utils.Const.INFO_JUZ
import com.acalapatih.oneayat.utils.Const.NOMOR_JUZ
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacaJuzActivity : BaseActivity<ActivityBacaJuzBinding>() {

    private val viewModel by viewModel<BacaJuzViewModel>()
    private lateinit var bacaJuzAdapter: BacaJuzAdapter
    private val nomorJuz by lazy { intent.getStringExtra(NOMOR_JUZ) }
    private val infoJuz by lazy { intent.getStringExtra(INFO_JUZ) }

    override fun getViewBinding(): ActivityBacaJuzBinding =
        ActivityBacaJuzBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        nomorJuz?.let { viewModel.getListAyat(it) }

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this@BacaJuzActivity)
        val itemDecoration = DividerItemDecoration(this@BacaJuzActivity, layoutManager.orientation)
        with(binding.rvAyat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    private fun initObserver() {
        viewModel.getListAyat.observe(this) { model ->
            when (model) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    model.data?.let { data ->
                        getListAyat(data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    model.message?.let {
                        println(it)
                        Toast.makeText(
                            this@BacaJuzActivity,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    @SuppressLint("SetTextI18n")
    private fun getListAyat(data: BacaJuzModel) {
        bacaJuzAdapter = BacaJuzAdapter(
            this@BacaJuzActivity,
            data.listAyat,
        )

        with(binding) {
            tvJuz.text = "Juz ${data.nomorJuz}"
            tvInfoJuz.text = "$infoJuz"
            rvAyat.adapter = bacaJuzAdapter
        }
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
                BookmarkActivity.start(this@BacaJuzActivity)
            }
        }
    }

    companion object {
        fun start(context: Context, nomorJuz: String, infoJuz: String) {
            val starter = Intent(context, BacaJuzActivity::class.java)
                .putExtra(NOMOR_JUZ, nomorJuz)
                .putExtra(INFO_JUZ, infoJuz)
            context.startActivity(starter)
        }
    }
}