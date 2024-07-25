package com.example.moneyapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.database.TransaksiDao
import com.example.moneyapp.database.TransaksiRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransaksiRepository(application: Application) {
    private val mTransaksiDao: TransaksiDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TransaksiRoomDatabase.getDatabase(application)
        mTransaksiDao = db.transaksiDao()
    }
    fun getAllNotes(): LiveData<List<Transaksi>> = mTransaksiDao.getAllTransaksi()
    fun insert(note: Transaksi) {
        executorService.execute { mTransaksiDao.insert(note) }
    }
    fun delete(note: Transaksi) {
        executorService.execute { mTransaksiDao.delete(note) }
    }
    fun update(note: Transaksi) {
        executorService.execute { mTransaksiDao.update(note) }
    }
    fun getTransaksiByDate(tanggal: String): LiveData<List<Transaksi>> =
        mTransaksiDao.getTransaksiByDate(tanggal)

    fun getTotalAmountByDate(tanggal: String): LiveData<Int> =
        mTransaksiDao.getTotalAmountByDate(tanggal)

}