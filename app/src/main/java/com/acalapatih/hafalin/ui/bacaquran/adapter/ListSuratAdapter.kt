package com.acalapatih.hafalin.ui.bacaquran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.hafalin.databinding.RecyclerviewListSuratBinding

class ListSuratAdapter(
    private val listSurat: List<ListSuratModel.GetListSurat>,
    private val isDarkModeActive: Boolean,
    val listener: OnUserClickListener? = null,
): RecyclerView.Adapter<ListSuratAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewListSuratBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: ListSuratModel.GetListSurat) {
            with(binding) {
                tvNomor.text = data.nomorSurat
                tvSurat.text = data.namaSurat
                tvInfoSurat.text =
                    "${data.artiSurat} | ${data.jumlahAyat} Ayat"

                cvSurat.setOnClickListener {
                    listener?.onUserClicked(data.nomorSurat, "bacaSurat")
                }

                if (isDarkModeActive) {
                    icNomor.setImageResource(R.drawable.ic_nomor_white)
                } else {
                    icNomor.setImageResource(R.drawable.ic_nomor_blue)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_list_surat, parent, false)
        )
    }

    override fun getItemCount(): Int = listSurat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listSurat[position])
    }

    interface OnUserClickListener {
        fun onUserClicked(nomorSurat: String, clicked: String)
    }
}