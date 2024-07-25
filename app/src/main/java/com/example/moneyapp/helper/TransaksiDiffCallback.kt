package com.example.moneyapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.moneyapp.database.Transaksi

class TransaksiDiffCallback(private val oldTransaksiList: List<Transaksi>, private val newTransaksiList: List<Transaksi>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTransaksiList.size
    override fun getNewListSize(): Int = newTransaksiList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTransaksiList[oldItemPosition].id == newTransaksiList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTransaksi = oldTransaksiList[oldItemPosition]
        val newTransaksi = newTransaksiList[newItemPosition]
        return oldTransaksi.id == newTransaksi.id
    }
}