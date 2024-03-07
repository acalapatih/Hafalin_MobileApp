package com.acalapatih.oneayat.ui.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acalapatih.oneayat.databinding.FragmentQuranBinding
import com.acalapatih.oneayat.ui.bacaquran.activity.BacaQuranActivity
import com.acalapatih.oneayat.ui.hafalanquran.activity.HafalanQuranActivity

class QuranFragment : Fragment() {

    private var _binding: FragmentQuranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            cvWidgetBacaQuran.setOnClickListener {
                BacaQuranActivity.start(requireContext())
            }
            cvWidgetHafalanQuran.setOnClickListener {
                HafalanQuranActivity.start(requireContext())
            }
        }
    }
}