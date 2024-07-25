package com.example.moneyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Transaksi::class], version = 3)
abstract class TransaksiRoomDatabase : RoomDatabase() {
    abstract fun transaksiDao(): TransaksiDao
    companion object {
        @Volatile
        private var INSTANCE: TransaksiRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): TransaksiRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TransaksiRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TransaksiRoomDatabase::class.java, "transaksi_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as TransaksiRoomDatabase
        }
    }
}