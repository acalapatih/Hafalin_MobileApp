package com.acalapatih.oneayat.ui.bacaquran.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.core.data.Resource
import com.acalapatih.oneayat.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.oneayat.databinding.FragmentListSuratBinding
import com.acalapatih.oneayat.ui.bacaquran.activity.BacaSuratActivity
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.ListSuratViewModel
import com.acalapatih.oneayat.ui.bacaquran.adapter.ListSuratAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListSuratFragment : Fragment(), ListSuratAdapter.OnUserClickListener {

    private var _binding: FragmentListSuratBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ListSuratViewModel>()
    private lateinit var listSuratAdapter: ListSuratAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListSuratBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListSurat()

        initView()
        initObserver()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        with(binding.rvSurat) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    private fun initObserver() {
        viewModel.getListSurat.observe(viewLifecycleOwner) { model ->
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
                        Toast.makeText(
                            requireContext(),
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

    private fun getListSurah(data: ListSuratModel) {
        listSuratAdapter = ListSuratAdapter(data.listSurat, this)
        binding.rvSurat.adapter = listSuratAdapter
    }

    override fun onUserClicked(nomorSurat: String, clicked: String) {
        BacaSuratActivity.start(requireContext(), nomorSurat)
    }
}