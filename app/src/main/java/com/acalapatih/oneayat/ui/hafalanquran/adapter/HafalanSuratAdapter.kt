package com.acalapatih.oneayat.ui.hafalanquran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.domain.model.hafalanquran.HafalanSuratModel
import com.acalapatih.oneayat.databinding.RecyclerviewHafalanSuratBinding

class HafalanSuratAdapter(
    private val listAyat: List<HafalanSuratModel.GetListAyat>,
    private val ayatDihafal: Int,
    val listener: OnUserClickListener? = null
): RecyclerView.Adapter<HafalanSuratAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewHafalanSuratBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: HafalanSuratModel.GetListAyat) {
            with(binding) {
                tvAyatSurat.text = "Ayat ${data.nomorAyat}"

                if (data.nomorAyat.toInt() <= ayatDihafal) {
                    itemView.isEnabled = true
                    itemView.alpha = 1.0f

                    cvHafalanSurat.setOnClickListener {
                        listener?.onUserClicked(data.nomorAyat, "detailAyat")
                    }
                } else {
                    itemView.isEnabled = false
                    itemView.alpha = 0.5f
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_hafalan_surat, parent, false)
        )
    }

    override fun getItemCount(): Int = listAyat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAyat[position])
    }

    interface OnUserClickListener {
        fun onUserClicked(nomorAyat: String, clicked: String)
    }
}