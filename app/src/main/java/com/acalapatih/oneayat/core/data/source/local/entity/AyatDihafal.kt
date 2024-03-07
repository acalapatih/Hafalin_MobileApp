package com.acalapatih.oneayat.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AyatDihafal(
    @PrimaryKey
    val id: Int = 1,
    val nomorSurat: String,
    val namaSurat: String? = null,
    val nomorAyat: String,
    val waktuHafalan: String,
) : Parcelable