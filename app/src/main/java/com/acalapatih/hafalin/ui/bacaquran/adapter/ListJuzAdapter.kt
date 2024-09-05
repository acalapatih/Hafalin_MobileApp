package com.acalapatih.hafalin.ui.bacaquran.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.source.local.entity.JuzQuran
import com.acalapatih.hafalin.databinding.RecyclerviewListJuzBinding

class ListJuzAdapter(
    private val context: Context,
    private val isDarkModeActive: Boolean,
    val listener: OnUserClickListener? = null,
): RecyclerView.Adapter<ListJuzAdapter.ViewHolder>() {

    private var listJuz = emptyList<JuzQuran>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setListJuz(listJuz: List<JuzQuran>) {
        this.listJuz = listJuz
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewListJuzBinding.bind(view)

        fun bindItem(data: JuzQuran) {
            with(binding) {
                tvNomor.text = data.nomorJuz
                tvJuz.text = data.namaJuz
                tvInfoJuz.text = data.infoJuz

                cvJuz.setOnClickListener {
                    listener?.onUserClicked(data.nomorJuz, data.infoJuz, "bacaJuz")
                }

                if (isDarkModeActive) {
                    icNomor.setImageResource(R.drawable.ic_nomor_white)
                } else {
                    icNomor.setImageResource(R.drawable.ic_nomor_green)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListJuzAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_list_juz, parent, false)
        )
    }

    override fun getItemCount(): Int = listJuz.size

    override fun onBindViewHolder(holder: ListJuzAdapter.ViewHolder, position: Int) {
        holder.bindItem(listJuz[position])
    }

    interface OnUserClickListener {
        fun onUserClicked(nomorJuz: String, infoJuz: String, clicked: String)
    }
}