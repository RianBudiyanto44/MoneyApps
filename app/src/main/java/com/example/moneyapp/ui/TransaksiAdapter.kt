package com.example.moneyapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.databinding.ItemTransaksiBinding
import com.example.moneyapp.helper.TransaksiDiffCallback

class TransaksiAdapter(
    private val viewModel: DaftarUangMasukViewModel,
    private val onEditClick: (Transaksi) -> Unit
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {
    private val listTransaksi = ArrayList<Transaksi>()
    fun setListTransaksi(listNotes: List<Transaksi>) {
        val diffCallback = TransaksiDiffCallback(this.listTransaksi, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTransaksi.clear()
        this.listTransaksi.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.bind(listTransaksi[position])
    }
    override fun getItemCount(): Int {
        return listTransaksi.size
    }
    inner class TransaksiViewHolder(private val binding: ItemTransaksiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaksi: Transaksi) {
            with(binding) {
                tvFromTo.text = "Dari ${transaksi.asal} ke ${transaksi.tujuan}"
                tvJumlah.text = "Rp ${transaksi.jumlah.toString()}"
                tvKeterangan.text = transaksi.keterangan
                tvTime.text = transaksi.time
//                tvItemTitle.text = note.title
//                tvItemDate.text = note.date
//                tvItemDescription.text = note.description
                btEdit.setOnClickListener {
//                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
//                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
//                    it.context.startActivity(intent)
                    onEditClick(transaksi)
                }
                btDelete.setOnClickListener {
                    viewModel.delete(transaksi)
                }

            }
        }
    }
}