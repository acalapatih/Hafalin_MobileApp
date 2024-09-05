package com.acalapatih.hafalin.ui.bookmark.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.hafalin.BaseActivity
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.factory.SettingViewModelFactory
import com.acalapatih.hafalin.core.data.source.local.preference.SettingPreferences
import com.acalapatih.hafalin.databinding.FragmentBookmarkBinding
import com.acalapatih.hafalin.ui.bookmark.activity.AyatDisimpan
import com.acalapatih.hafalin.ui.bookmark.adapter.BookmarkAdapter
import com.acalapatih.hafalin.ui.bookmark.viewmodel.BookmarkViewModel
import com.acalapatih.hafalin.ui.setting.SettingViewModel
import com.acalapatih.hafalin.utils.dataStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<BookmarkViewModel>()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
        val pengaturanPref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pengaturanPref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            with(binding) {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    icTerakhirDibaca.setImageResource(R.drawable.ic_dibaca_white)
                    icAyatFavorit.setImageResource(R.drawable.ic_ayat_favorit_white)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    icTerakhirDibaca.setImageResource(R.drawable.ic_dibaca_green)
                    icAyatFavorit.setImageResource(R.drawable.ic_ayat_favorit_green)
                }
            }
        }

        bookmarkAdapter = BookmarkAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        with(binding.rvAyat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
            adapter = bookmarkAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.ayatDibaca.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                with(binding.tvAyatDibaca) {
                    text = "Q.S ${data.namaSurat} : ${data.nomorAyat}"

                    setOnClickListener {
                        AyatDisimpan.start(requireContext(), data.nomorSurat, data.nomorAyat)
                    }
                }
            } else {
                binding.tvAyatDibaca.text = "Belum ada ayat yang ditandai"
            }
        }

        viewModel.getAllAyatFavorit().observe(viewLifecycleOwner) { listAyatFavorit ->
            if (listAyatFavorit != null) {
                bookmarkAdapter.setListFavorite(listAyatFavorit)

                bookmarkAdapter.hapusAyatFavorit = { ayatFavorit ->
                    val baseActivity = activity as? BaseActivity<*>
                    baseActivity?.showDialogHapusAyatFavorit {
                        viewModel.delete(ayatFavorit)
                    }
                }
            } else {
                binding.tvNoDataAyatFavorit.isVisible = true
            }
        }
    }
}