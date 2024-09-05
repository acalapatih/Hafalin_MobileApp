package com.acalapatih.hafalin.ui.bookmark.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.source.local.entity.AyatFavorit
import com.acalapatih.hafalin.databinding.RecyclerviewAyatFavoritBinding
import com.acalapatih.hafalin.ui.bookmark.activity.AyatDisimpan
import com.acalapatih.hafalin.utils.AyatCallback

class BookmarkAdapter(
    private val context: Context
): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    private val listAyatFavorit = ArrayList<AyatFavorit>()
    var hapusAyatFavorit: ((ayatFavorit: AyatFavorit) -> Unit?)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAyatFavoritBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: AyatFavorit) {
            with(binding) {
                tvLabelAyatFavorit.text = "Q.S ${data.namaSurat} : ${data.nomorAyat}"

                cvAyatFavorit.setOnClickListener {
                    AyatDisimpan.start(context, data.nomorSurat, data.nomorAyat)
                }

                icHapusAyatFavorit.setOnClickListener {
                    val ayatFavorit = AyatFavorit(data.nomorSurat, data.namaSurat, data.nomorAyat)
                    hapusAyatFavorit?.invoke(ayatFavorit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_ayat_favorit, parent, false)
        )
    }

    override fun getItemCount(): Int = listAyatFavorit.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAyatFavorit[position])
    }

    fun setListFavorite(listUser: List<AyatFavorit>) {
        val diffCallback = AyatCallback(listAyatFavorit, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listAyatFavorit.clear()
        listAyatFavorit.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }
}