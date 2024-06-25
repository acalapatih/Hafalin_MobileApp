package com.acalapatih.oneayat.ui.bacaquran.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.core.data.source.local.entity.AyatDibaca
import com.acalapatih.oneayat.core.data.source.local.entity.AyatFavorit
import com.acalapatih.oneayat.core.domain.model.bacaquran.BacaJuzModel
import com.acalapatih.oneayat.databinding.RecyclerviewAyatBinding
import com.google.api.Visibility

class BacaJuzAdapter(
    private val context: Context,
    private val listAyat: List<BacaJuzModel.GetListAyat>,
    private val isDarkModeActive: Boolean,
    val listener: BacaJuzAdapter.OnUserClickListener? = null
): RecyclerView.Adapter<BacaJuzAdapter.ViewHolder>() {
    var ayatFavoritSelected: ((ayatFavorit: AyatFavorit, nomorAyat: String, icFavorit: ImageView) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAyatBinding.bind(view)

        fun bindItem(data: BacaJuzModel.GetListAyat) {
            with(binding) {
                tvNomor.text = data.nomorAyat
                tvAyat.text = data.lafadzAyat
                tvTerjemahanAyat.text = data.terjemahanAyat

//                icTandai.setOnClickListener {
//                    val ayatDibaca = AyatDibaca(1, nomorSurat, namaSurat, data.nomorAyat)
//                    listener?.onUserClickedTandai(ayatDibaca, "terakhirDibaca")
//                }
//
//                icFavorit.setOnClickListener {
//                    val ayatFavorit = AyatFavorit(nomorSurat, namaSurat, data.nomorAyat)
//                    ayatFavoritSelected?.invoke(ayatFavorit, data.nomorAyat, icFavorit)
//                }

                icTandai.visibility = View.GONE
                icFavorit.visibility = View.GONE
                icAudio.setOnClickListener {
                    val audioAyatPlayer = MediaPlayer.create(context, data.audioAyat.toUri())
                    audioAyatPlayer.start()
                    listener?.onUserClickedAudio(data.nomorAyat, audioAyatPlayer, "audioAyat")
                }

                if (isDarkModeActive) {
                    icAudio.setImageResource(R.drawable.ic_audio_white)
                    icNomor.setImageResource(R.drawable.ic_nomor_white)
                } else {
                    icAudio.setImageResource(R.drawable.ic_audio_green)
                    icNomor.setImageResource(R.drawable.ic_nomor_green)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BacaJuzAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_ayat, parent, false)
        )
    }

    override fun getItemCount(): Int = listAyat.size

    override fun onBindViewHolder(holder: BacaJuzAdapter.ViewHolder, position: Int) {
        holder.bindItem(listAyat[position])
    }

    interface OnUserClickListener {
        fun onUserClickedTandai(ayatDibaca: AyatDibaca, clicked: String)
        fun onUserClickedAudio(nomorAyat: String, audioAyatPlayer: MediaPlayer, clicked: String)
    }
}