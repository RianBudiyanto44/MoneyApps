package com.example.moneyapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.repository.TransaksiRepository

class InputUangMasukViewModel(application: Application) : ViewModel() {
    private val mTransaksiRepository: TransaksiRepository = TransaksiRepository(application)
    fun insert(note: Transaksi) {
        mTransaksiRepository.insert(note)
    }
    fun update(note: Transaksi) {
        mTransaksiRepository.update(note)
    }

}