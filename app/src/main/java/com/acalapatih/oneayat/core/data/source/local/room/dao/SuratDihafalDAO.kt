package com.acalapatih.oneayat.core.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.acalapatih.oneayat.core.data.source.local.entity.*

@Dao
interface AlFatihahDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alFatihah: AlFatihah)

    @Query("SELECT * FROM AlFatihah ORDER BY nomorAyat ASC")
    fun getAlFatihah(): LiveData<List<AlFatihah>>
}

@Dao
interface AnNasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(anNas: AnNas)

    @Query("SELECT * FROM AnNas ORDER BY nomorAyat ASC")
    fun getAnNas(): LiveData<List<AnNas>>
}

@Dao
interface AlFalaqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alFalaq: AlFalaq)

    @Query("SELECT * FROM AlFalaq ORDER BY nomorAyat ASC")
    fun getAlFalaq(): LiveData<List<AlFalaq>>
}

@Dao
interface AlIkhlasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AlIkhlas: AlIkhlas)

    @Query("SELECT * FROM AlIkhlas ORDER BY nomorAyat ASC")
    fun getAlIkhlas(): LiveData<List<AlIkhlas>>
}

@Dao
interface AtTakwirDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AtTakwir: AtTakwir)

    @Query("SELECT * FROM AtTakwir ORDER BY nomorAyat ASC")
    fun getAtTakwir(): LiveData<List<AtTakwir>>
}

@Dao
interface AnNabaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AnNaba: AnNaba)

    @Query("SELECT * FROM AnNaba ORDER BY nomorAyat ASC")
    fun getAnNaba(): LiveData<List<AnNaba>>
}

@Dao
interface AlMulkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alMulk: AlMulk)

    @Query("SELECT * FROM AlMulk ORDER BY nomorAyat ASC")
    fun getAlMulk(): LiveData<List<AlMulk>>
}

@Dao
interface AlKahfiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alKahfi: AlKahfi)

    @Query("SELECT * FROM AlKahfi ORDER BY nomorAyat ASC")
    fun getAlKahfi(): LiveData<List<AlKahfi>>
}