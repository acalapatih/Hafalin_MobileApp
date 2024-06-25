package com.acalapatih.oneayat.core.domain.repository.bookmark

import android.app.Application
import androidx.lifecycle.LiveData
import com.acalapatih.oneayat.core.data.source.local.entity.*
import com.acalapatih.oneayat.core.data.source.local.room.dao.*
import com.acalapatih.oneayat.core.data.source.local.room.database.SuratDihafalDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SuratDihafalRepository(application: Application) {
    private val alFatihahDao: AlFatihahDao
    private val anNasDao: AnNasDao
    private val alFalaqDao: AlFalaqDao
    private val alIkhlasDao: AlIkhlasDao
    private val alLahabDao: AlLahabDao
    private val anNasrDao: AnNasrDao
    private val alKafirunDao: AlKafirunDao
    private val atTakwirDao: AtTakwirDao
    private val anNabaDao: AnNabaDao
    private val alMulkDao: AlMulkDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SuratDihafalDatabase.getDatabase(application)
        alFatihahDao = db.alFatihahDao()
        anNasDao = db.anNasDao()
        alFalaqDao = db.alFalaqDao()
        alIkhlasDao = db.alIkhlasDao()
        alLahabDao = db.alLahabDao()
        anNasrDao = db.anNasrDao()
        alKafirunDao = db.alKafirunDao()
        atTakwirDao = db.atTakwirDao()
        anNabaDao = db.anNabaDao()
        alMulkDao = db.alMulkDao()
    }

    //Al-Fatihah
    fun insertAlFatihah(alFatihah: AlFatihah) {
        executorService.execute { alFatihahDao.insert(alFatihah) }
    }
    fun getAllAlFatihah() = alFatihahDao.getAlFatihah()

    //An-Nas
    fun insertAnNas(anNas: AnNas) {
        executorService.execute { anNasDao.insert(anNas) }
    }
    fun getAllAnNas() = anNasDao.getAnNas()

    //Al-Falaq
    fun insertAlFalaq(alFalaq: AlFalaq) {
        executorService.execute { alFalaqDao.insert(alFalaq) }
    }
    fun getAllAlFalaq() = alFalaqDao.getAlFalaq()

    //Al-Ikhlas
    fun insertAlIkhlas(alIkhlas: AlIkhlas) {
        executorService.execute { alIkhlasDao.insert(alIkhlas) }
    }
    fun getAllAlIkhlas() = alIkhlasDao.getAlIkhlas()

    //Al-Lahab
    fun insertAlLahab(alLahab: AlLahab) {
        executorService.execute { alLahabDao.insert(alLahab) }
    }
    fun getAllAlLahab() = alLahabDao.getAlLahab()

    //An-Nasr
    fun insertAnNasr(anNasr: AnNasr) {
        executorService.execute { anNasrDao.insert(anNasr) }
    }
    fun getAllAnNasr() = anNasrDao.getAllAnNasr()

    //Al-Kafirun
    fun insertAlKafirun(alKafirun: AlKafirun) {
        executorService.execute { alKafirunDao.insert(alKafirun) }
    }
    fun getAllAlKafirun() = alKafirunDao.getAlKafirun()

    //At-Takwir
    fun insertAtTakwir(atTakwir: AtTakwir) {
        executorService.execute { atTakwirDao.insert(atTakwir) }
    }
    fun getAllAtTakwir() = atTakwirDao.getAtTakwir()

    //An-Naba
    fun insertAnNaba(anNaba: AnNaba) {
        executorService.execute { anNabaDao.insert(anNaba) }
    }
    fun getAllAnNaba() = anNabaDao.getAnNaba()

    //Al-Mulk
    fun insertAlMulk(alMulk: AlMulk) {
        executorService.execute { alMulkDao.insert(alMulk) }
    }
    fun getAllAlMulk() = alMulkDao.getAlMulk()
}
