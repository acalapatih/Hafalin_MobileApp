package com.acalapatih.oneayat.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(primaryKeys = ["nomorSurat", "nomorAyat"])
@Parcelize
data class AyatFavorit(
    val nomorSurat: String,
    val namaSurat: String? = null,
    val nomorAyat: String
) : Parcelable
