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
interface AlLahabDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AlLahab: AlLahab)

    @Query("SELECT * FROM AlLahab ORDER BY nomorAyat ASC")
    fun getAlLahab(): LiveData<List<AlLahab>>
}

@Dao
interface AnNasrDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AnNasr: AnNasr)

    @Query("SELECT * FROM AnNasr ORDER BY nomorAyat ASC")
    fun getAllAnNasr(): LiveData<List<AnNasr>>
}

@Dao
interface AlKafirunDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alKafirun: AlKafirun)

    @Query("SELECT * FROM AlKafirun ORDER BY nomorAyat ASC")
    fun getAlKafirun(): LiveData<List<AlKafirun>>
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