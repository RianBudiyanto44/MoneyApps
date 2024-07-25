package com.example.moneyapp.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneyapp.R
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.databinding.FragmentDaftarUangMasukBinding
import com.example.moneyapp.databinding.FragmentInputUangMasukBinding
import com.example.moneyapp.helper.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DaftarUangMasukFragment : Fragment() {

    companion object {
        fun newInstance() = DaftarUangMasukFragment()
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var transaksi: Transaksi? = null
    private lateinit var viewModel: DaftarUangMasukViewModel
    private lateinit var adapter: TransaksiAdapter
    //private var selectedDate: String? = null

    private var _binding: FragmentDaftarUangMasukBinding? = null
    private val binding get() = _binding!!
//    private lateinit var viewModel: DaftarUangMasukViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_daftar_uang_masuk, container, false)
        _binding = FragmentDaftarUangMasukBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = obtainViewModel(this)
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            binding.tvTanggal.text = date
            if(date != "semua transaksi") {
                showToast(date.toString())
                viewModel.getTransaksiByDate(date.toString()).observe(viewLifecycleOwner) { transaksiList ->
                    if (transaksiList != null) {
                        adapter.setListTransaksi(transaksiList)
                    }
                }
                viewModel.getTotalAmountByDate(date.toString()).observe(viewLifecycleOwner, Observer { totalAmount ->
                    // Menampilkan total saldo, jika totalAmount adalah null, tampilkan 0
                    binding.tvTotal.text = totalAmount?.let { "Rp ${it.toString()}" } ?: "0"
                })
            } else {
                viewModel.getAllNotes().observe(viewLifecycleOwner) { transaksiList ->
                    if (transaksiList != null) {
                        adapter.setListTransaksi(transaksiList)
                    }
                }
            }

        })


        //adapter = TransaksiAdapter(viewModel)
        adapter = TransaksiAdapter(viewModel) { item ->
            // Handle edit click
//            val action = InputUangMasukFragment.actionYourFragmentToEditFragment(item)
//            findNavController().navigate(action)
            val bundle = Bundle().apply {
                putParcelable("item", item)
            }
            // Menavigasi ke EditFragment dengan data
            val editFragment = InputUangMasukFragment().apply {
                arguments = bundle
            }
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, editFragment)
                .addToBackStack(null)
                .commit()
        }

        binding?.rvTransaksi?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvTransaksi?.setHasFixedSize(true)
        binding?.rvTransaksi?.adapter = adapter

//        binding?.fabAdd?.setOnClickListener {
//            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
//            startActivity(intent)
//        }

        binding.btInput.setOnClickListener {
            val categoryFragment = InputUangMasukFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, categoryFragment, InputUangMasukFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        binding.edtDate.setOnClickListener {
            showDatePicker()

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set binding ke null untuk menghindari memory leak
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(DaftarUangMasukViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun obtainViewModel(fragment: Fragment): DaftarUangMasukViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory).get(DaftarUangMasukViewModel::class.java)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay)

                // Format tanggal menjadi dd/MM/yy
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val formatedDate = dateFormat.format(selectedDateCalendar.time).toString()
                viewModel.setSelectedDate(formatedDate)

            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

//    private fun showAlertDialog(type: Int) {
//        val isDialogClose = type == ALERT_DIALOG_CLOSE
//        val dialogTitle: String
//        val dialogMessage: String
//        if (isDialogClose) {
//            dialogTitle = getString(R.string.cancel)
//            dialogMessage = getString(R.string.message_cancel)
//        } else {
//            dialogMessage = getString(R.string.message_delete)
//            dialogTitle = getString(R.string.delete)
//        }
//        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
//        with(alertDialogBuilder) {
//            setTitle(dialogTitle)
//            setMessage(dialogMessage)
//            setCancelable(false)
//            setPositiveButton(getString(R.string.yes)) { _, _ ->
//                if (!isDialogClose) {
//                    viewModel.delete(transaksi as Transaksi)
//                    showToast(getString(R.string.deleted))
//                }
//                parentFragmentManager.popBackStack()
//            }
//            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
//        }
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }

    internal var optionDialogListener: LihatFotoFragment.OnOptionDialogListener = object : LihatFotoFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }

}