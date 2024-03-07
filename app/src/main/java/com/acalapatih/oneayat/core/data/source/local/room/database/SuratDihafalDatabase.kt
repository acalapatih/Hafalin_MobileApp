package com.acalapatih.oneayat.core.data.source.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acalapatih.oneayat.core.data.source.local.entity.*
import com.acalapatih.oneayat.core.data.source.local.room.dao.*

@Database(entities = [
    AlFatihah::class,
    AnNas::class,
    AlFalaq::class,
    AlIkhlas::class,
    AtTakwir::class,
    AnNaba::class,
    AlMulk::class,
    AlKahfi::class], version = 1)
abstract class SuratDihafalDatabase : RoomDatabase() {
    abstract fun alFatihahDao(): AlFatihahDao
    abstract fun anNasDao(): AnNasDao
    abstract fun alFalaqDao(): AlFalaqDao
    abstract fun alIkhlasDao(): AlIkhlasDao
    abstract fun atTakwirDao(): AtTakwirDao
    abstract fun anNabaDao(): AnNabaDao
    abstract fun alMulkDao(): AlMulkDao
    abstract fun alKahfiDao(): AlKahfiDao

    companion object {
        @Volatile
        private var INSTANCE: SuratDihafalDatabase? = null

        fun getDatabase(context: Context): SuratDihafalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SuratDihafalDatabase::class.java,
                    "suratDihafal"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}