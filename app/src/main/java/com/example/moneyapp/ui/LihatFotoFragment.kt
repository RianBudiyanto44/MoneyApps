package com.example.moneyapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.moneyapp.R
import com.example.moneyapp.databinding.FragmentDialogImageSelecetionBinding
import com.example.moneyapp.databinding.FragmentLihatFotoBinding


class LihatFotoFragment : DialogFragment() {
    private var _binding: FragmentLihatFotoBinding? = null
    private val binding get() = _binding!!
//    private var optionDialogListener: FragmentLihatFotoBinding.OnOptionDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_lihat_foto, container, false)
        _binding = FragmentLihatFotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.imgCamera.setOnClickListener {
//            optionDialogListener?.onOptionChosen("kamera")
//            dialog?.dismiss()
//        }
//
//        binding.imgGallery.setOnClickListener {
//            optionDialogListener?.onOptionChosen("galeri")
//            dialog?.dismiss()
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is DaftarUangMasukFragment) {
//            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
//        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }

    companion object {

    }
}