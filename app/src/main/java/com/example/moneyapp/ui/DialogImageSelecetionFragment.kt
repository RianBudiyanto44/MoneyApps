package com.example.moneyapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.moneyapp.databinding.FragmentDialogImageSelecetionBinding

class DialogImageSelecetionFragment : DialogFragment() {
    private var _binding: FragmentDialogImageSelecetionBinding? = null
    private val binding get() = _binding!!
    private var optionDialogListener: OnOptionDialogListener? = null
//    override fun onView(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDialogImageSelecetionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        btnChoose = view.findViewById(R.id.btn_choose)
//        btnClose = view.findViewById(R.id.btn_close)
//        rgOptions = view.findViewById(R.id.rg_options)
//        rbSaf = view.findViewById(R.id.rb_saf)
//        rbLvg = view.findViewById(R.id.rb_lvg)
//        rbMou = view.findViewById(R.id.rb_mou)
//        rbMoyes = view.findViewById(R.id.rb_moyes)
//        btnChoose.setOnClickListener {
//            val checkedRadioButtonId = rgOptions.checkedRadioButtonId
//            if (checkedRadioButtonId != -1) {
//                val coach: String? = when (checkedRadioButtonId) {
//                    R.id.rb_saf -> rbSaf.text.toString().trim()
//                    R.id.rb_mou -> rbMou.text.toString().trim()
//                    R.id.rb_lvg -> rbLvg.text.toString().trim()
//                    R.id.rb_moyes -> rbMoyes.text.toString().trim()
//                    else -> null
//                }
//                optionDialogListener?.onOptionChosen(coach)
//                dialog?.dismiss()
//            }
//        }
//        btnClose.setOnClickListener {
//            dialog?.cancel()
//        }
        binding.imgCamera.setOnClickListener {
            optionDialogListener?.onOptionChosen("kamera")
            dialog?.dismiss()
        }

        binding.imgGallery.setOnClickListener {
            optionDialogListener?.onOptionChosen("galeri")
            dialog?.dismiss()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
//                findNavController().navigateUp()
                parentFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set binding ke null untuk menghindari memory leak
        _binding = null
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*
        Saat attach maka set optionDialogListener dengan listener dari detailCategoryFragment
         */
        val fragment = parentFragment

        if (fragment is InputUangMasukFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()

        /*
        Saat detach maka set null pada optionDialogListener
         */
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}