package com.acalapatih.oneayat.ui.bookmark.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.BaseActivity
import com.acalapatih.oneayat.databinding.FragmentBookmarkBinding
import com.acalapatih.oneayat.ui.bookmark.activity.AyatDisimpan
import com.acalapatih.oneayat.ui.bookmark.adapter.BookmarkAdapter
import com.acalapatih.oneayat.ui.bookmark.viewmodel.BookmarkViewModel
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
        viewModel.ayatDibaca.observe(this) { data ->
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

        viewModel.getAllAyatFavorit().observe(this) { listAyatFavorit ->
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