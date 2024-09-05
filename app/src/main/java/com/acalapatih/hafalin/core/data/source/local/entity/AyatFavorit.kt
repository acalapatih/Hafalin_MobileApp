package com.acalapatih.hafalin.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(primaryKeys = ["nomorSurat", "nomorAyat"])
@Parcelize
data class AyatFavorit(
    val nomorSurat: String,
    val namaSurat: String? = null,
    val nomorAyat: String
) : Parcelable
