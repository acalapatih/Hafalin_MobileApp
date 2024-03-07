package com.acalapatih.oneayat.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "AlFatihah")
@Parcelize
data class AlFatihah(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AnNas")
@Parcelize
data class AnNas(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AlFalaq")
@Parcelize
data class AlFalaq(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AlIkhlas")
@Parcelize
data class AlIkhlas(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AtTakwir")
@Parcelize
data class AtTakwir(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AnNaba")
@Parcelize
data class AnNaba(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AlMulk")
@Parcelize
data class AlMulk(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable

@Entity(tableName = "AlKahfi")
@Parcelize
data class AlKahfi(
    @PrimaryKey
    val idAyat: Int,
    val namaSurat: String,
    val nomorAyat: Int,
    val statusAyat: String
) : Parcelable
