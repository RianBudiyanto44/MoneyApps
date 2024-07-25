package com.example.moneyapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.repository.TransaksiRepository

class DaftarUangMasukViewModel(application: Application) : ViewModel() {
    // TODO: Implement the ViewModel
    private val mTransaksiRepository: TransaksiRepository = TransaksiRepository(application)
    fun getAllNotes(): LiveData<List<Transaksi>> = mTransaksiRepository.getAllNotes()

    fun delete(note: Transaksi) {
        mTransaksiRepository.delete(note)
    }

    fun getTransaksiByDate(tanggal: String): LiveData<List<Transaksi>> =
        mTransaksiRepository.getTransaksiByDate(tanggal)

    // LiveData untuk menyimpan tanggal yang dipilih
    private val _selectedDate = MutableLiveData<String>().apply {
        value = "semua transaksi" // Menetapkan nilai default di sini
    }
    val selectedDate: LiveData<String> get() = _selectedDate

    // Fungsi untuk mengupdate tanggal yang dipilih
    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun getTotalAmountByDate(date: String): LiveData<Int> {
        return mTransaksiRepository.getTotalAmountByDate(date)
    }
}