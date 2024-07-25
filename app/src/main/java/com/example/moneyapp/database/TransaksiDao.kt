package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface TransaksiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaksi: Transaksi)
    @Update
    fun update(transaksi: Transaksi)
    @Delete
    fun delete(transaksi: Transaksi)
    @Query("SELECT * from transaksi ORDER BY id ASC")
    fun getAllTransaksi(): LiveData<List<Transaksi>>
    @Query("SELECT * FROM transaksi WHERE date = :tanggal")
    fun getTransaksiByDate(tanggal: String): LiveData<List<Transaksi>>
    @Query("SELECT SUM(jumlah) FROM transaksi WHERE date = :tanggal")
    fun getTotalAmountByDate(tanggal: String): LiveData<Int>
}