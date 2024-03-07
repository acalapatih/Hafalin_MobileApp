package com.acalapatih.oneayat.ui.bacaquran.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.domain.model.bacaquran.ListSuratModel
import com.acalapatih.oneayat.databinding.RecyclerviewListSuratBinding

class ListSuratAdapter(
    private val listSurat: List<ListSuratModel.GetListSurat>,
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