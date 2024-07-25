package com.example.moneyapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "date")
    var date: String? = null,
    @ColumnInfo(name = "time")
    var time: String? = null,
    @ColumnInfo(name = "tujuan")
    var tujuan: String? = null,
    @ColumnInfo(name = "asal")
    var asal: String? = null,
    @ColumnInfo(name = "keterangan")
    var keterangan: String? = null,
    @ColumnInfo(name = "jenis")
    var jenis: String? = null,
    @ColumnInfo(name = "jumlah")
    var jumlah: Int? = null,
    @ColumnInfo(name = "foto")
    var foto: String? = null,
) : Parcelable