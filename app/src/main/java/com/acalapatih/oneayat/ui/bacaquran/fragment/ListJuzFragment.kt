package com.acalapatih.oneayat.ui.bacaquran.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalapatih.oneayat.core.data.source.local.entity.JuzQuran
import com.acalapatih.oneayat.databinding.FragmentListJuzBinding
import com.acalapatih.oneayat.ui.bacaquran.activity.BacaJuzActivity
import com.acalapatih.oneayat.ui.bacaquran.adapter.ListJuzAdapter
import com.acalapatih.oneayat.ui.bacaquran.viewmodel.ListJuzViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListJuzFragment : Fragment(), ListJuzAdapter.OnUserClickListener {

    private var _binding: FragmentListJuzBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ListJuzViewModel>()
    private lateinit var listJuzAdapter: ListJuzAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListJuzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
        listJuzAdapter = ListJuzAdapter(requireContext(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        with(binding.rvJuz) {
            setLayoutManager(layoutManager)
            addItemDecoration(itemDecoration)
        }
    }

    private fun initObserver() {
        viewModel.allListJuz.observe(this) { listJuz ->
            listJuz?.let { data ->
                getListJuz(data)
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun getListJuz(data: List<JuzQuran>) {
        listJuzAdapter.setListJuz(data)
        binding.rvJuz.adapter = listJuzAdapter
    }

    override fun onUserClicked(nomorJuz: String, infoJuz: String, clicked: String) {
        BacaJuzActivity.start(requireContext(), nomorJuz, infoJuz)
    }
}