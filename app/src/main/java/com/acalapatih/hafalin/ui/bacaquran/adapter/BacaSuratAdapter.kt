package com.acalapatih.hafalin.ui.bacaquran.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.acalapatih.hafalin.R
import com.acalapatih.hafalin.core.data.source.local.entity.AyatDibaca
import com.acalapatih.hafalin.core.domain.model.bacaquran.BacaSuratModel
import com.acalapatih.hafalin.core.data.source.local.entity.AyatFavorit
import com.acalapatih.hafalin.databinding.RecyclerviewAyatBinding

class BacaSuratAdapter(
    private val context: Context,
    private val namaSurat: String,
    private val nomorSurat: String,
    private val listAyat: List<BacaSuratModel.GetListAyat>,
    private val isDarkModeActive: Boolean,
    val listener: OnUserClickListener? = null
): RecyclerView.Adapter<BacaSuratAdapter.ViewHolder>() {

    var ayatFavoritSelected: ((ayatFavorit: AyatFavorit, nomorAyat: String, icFavorit: ImageView) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAyatBinding.bind(view)

        fun bindItem(data: BacaSuratModel.GetListAyat) {
            with(binding) {
                tvNomor.text = data.nomorAyat
                tvAyat.text = data.lafadzAyat
                tvTerjemahanAyat.text = data.terjemahanAyat

                icTandai.setOnClickListener {
                    val ayatDibaca = AyatDibaca(1, nomorSurat, namaSurat, data.nomorAyat)
                    listener?.onUserClickedTandai(ayatDibaca, "terakhirDibaca")
                }

                icFavorit.setOnClickListener {
                    val ayatFavorit = AyatFavorit(nomorSurat, namaSurat, data.nomorAyat)
                    ayatFavoritSelected?.invoke(ayatFavorit, data.nomorAyat, icFavorit)
                }

                icAudio.setOnClickListener {
                    val audioAyatPlayer = MediaPlayer.create(context, data.audioAyat.toUri())
                    audioAyatPlayer.start()
                    listener?.onUserClickedAudio(data.nomorAyat, audioAyatPlayer, "audioAyat")
                }

                if (isDarkModeActive) {
                    icAudio.setImageResource(R.drawable.ic_audio_white)
                    icTandai.setImageResource(R.drawable.ic_tandai_white)
                    icNomor.setImageResource(R.drawable.ic_nomor_white)
                    icFavorit.setImageResource(R.drawable.ic_favorit_white)
                } else {
                    icAudio.setImageResource(R.drawable.ic_audio_green)
                    icTandai.setImageResource(R.drawable.ic_tandai_green)
                    icNomor.setImageResource(R.drawable.ic_nomor_green)
                    icFavorit.setImageResource(R.drawable.ic_favorit_green)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BacaSuratAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_ayat, parent, false)
        )
    }

    override fun getItemCount(): Int = listAyat.size

    override fun onBindViewHolder(holder: BacaSuratAdapter.ViewHolder, position: Int) {
        holder.bindItem(listAyat[position])
    }

    interface OnUserClickListener {
        fun onUserClickedTandai(ayatDibaca: AyatDibaca, clicked: String)
        fun onUserClickedAudio(nomorAyat: String, audioAyatPlayer: MediaPlayer, clicked: String)
    }
}