package com.example.moneyapp.ui

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.moneyapp.R
import com.example.moneyapp.database.Transaksi
import com.example.moneyapp.databinding.FragmentDaftarUangMasukBinding
import com.example.moneyapp.databinding.FragmentInputUangMasukBinding
import com.example.moneyapp.helper.DateHelper
import com.example.moneyapp.helper.ViewModelFactory
import com.example.moneyapp.helper.getImageUri
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class InputUangMasukFragment : Fragment() {

    companion object {
        fun newInstance() = InputUangMasukFragment()
    }
    private val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1001
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private var imageView: ImageView? = null

    private var _binding: FragmentInputUangMasukBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InputUangMasukViewModel

    private var transaksi: Transaksi? = null
    private var isEdit = false
    private var currentImageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            currentImageUri = it
//            binding.previewImageView.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_input_uang_masuk, container, false)
        _binding = FragmentInputUangMasukBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(InputUangMasukViewModel::class.java)
        viewModel = obtainViewModel(this)
        // TODO: Use the ViewModel


        //transaksi = Transaksi()
//        arguments?.let { bundle ->
//            transaksi = bundle.getParcelable("item")!!
//            // Gunakan data item
//            binding.edtAsal.setText(transaksi!!.asal)
//            binding.edtTujuan.setText(transaksi!!.tujuan)
//            transaksi!!.jumlah?.let { binding.edtJumlah.setText(it) }
//            binding.edtKeterangan.setText(transaksi!!.keterangan)
//            binding.edtJenis.setText(transaksi!!.jenis)
//        }

        arguments?.let { bundle ->
            transaksi = bundle.getParcelable("item")!!
        }

        if (transaksi != null) {
            isEdit = true
        } else {
            transaksi = Transaksi()
        }

        if (isEdit) {
            if (transaksi != null) {
                transaksi?.let { transaksi ->
                    binding.edtAsal.setText(transaksi.asal)
                    binding.edtTujuan.setText(transaksi.tujuan)
                    binding.edtJumlah.setText(transaksi.jumlah.toString())
//                    transaksi.jumlah?.let { binding.edtJumlah.setText(it) }
                    binding.edtKeterangan.setText(transaksi.keterangan)
                    binding.edtJenis.setText(transaksi.jenis)
//                    binding.previewImageView.setImageURI(transaksi.foto?.toUri())
//                    currentImageUri = transaksi.foto?.let { Uri.parse(it) }
//                    getContent.launch("image/*")
                }
            }
        }

        binding?.btnSubmit?.setOnClickListener {
            val asal = binding?.edtAsal?.text.toString().trim()
            val tujuan = binding?.edtTujuan?.text.toString().trim()
            val jumlah = binding?.edtJumlah?.text.toString().trim()
            val keterangan = binding?.edtKeterangan?.text.toString().trim()
            val jenis = binding?.edtJenis?.text.toString().trim()

            when {
                asal.isEmpty() -> {
                    binding?.edtAsal?.error = getString(R.string.empty)
                }
                tujuan.isEmpty() -> {
                    binding?.edtTujuan?.error = getString(R.string.empty)
                }
                jumlah.isEmpty() -> {
                    binding?.edtJumlah?.error = getString(R.string.empty)
                }
                keterangan.isEmpty() -> {
                    binding?.edtKeterangan?.error = getString(R.string.empty)
                }
                jenis.isEmpty() -> {
                    binding?.edtJenis?.error = getString(R.string.empty)
                }
                else -> {
                    submitImage()

                    transaksi.let { transaksi ->
                        transaksi?.asal = asal
                        transaksi?.tujuan = tujuan
                        transaksi?.jumlah = jumlah.toInt()
                        transaksi?.keterangan = keterangan
                        transaksi?.jenis = jenis
                        currentImageUri?.let {
                            transaksi?.foto = it.toString()
                        }
                    }
                    if (isEdit) {
                        viewModel.update(transaksi as Transaksi)
                        showToast(getString(R.string.changed))
                    } else {
                        transaksi.let { note ->
                            note?.date = DateHelper.getCurrentDate()
                            note?.time= DateHelper.getCurrentTime()
                        }
                        viewModel.insert(transaksi as Transaksi)
                        showToast(getString(R.string.added))
                    }
//                    transaksi.let { note ->
//                        note?.date = DateHelper.getCurrentDate()
//                        note?.time= DateHelper.getCurrentTime()
//                    }
//                    viewModel.insert(transaksi as Transaksi)
//                    showToast(getString(R.string.added))
                    parentFragmentManager.popBackStack()
                }
            }
        }

        binding.previewImageView.setOnClickListener {
            val optionDialogFragment = DialogImageSelecetionFragment()

            val fragmentManager = childFragmentManager
            optionDialogFragment.show(fragmentManager, DialogImageSelecetionFragment::class.java.simpleName)
        }


    }

    private fun obtainViewModel(fragment: Fragment): InputUangMasukViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory).get(InputUangMasukViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast("No media selected")
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun showImage() {
        currentImageUri?.let {

            binding.previewImageView.setImageURI(it)
        }
    }

    private fun submitImage() {
        currentImageUri?.let {
            checkPermissionAndSaveImage(it)
        }
    }

    private fun checkPermissionAndSaveImage(uri: Uri) {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
        } else {
            saveImage(requireActivity(), uri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    currentImageUri?.let { saveImage(requireActivity(), it) }
                } else {
                    showToast("Permission denied")
                }
            }
        }
    }

    internal var optionDialogListener = object : DialogImageSelecetionFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            if(text == "galeri") {
                startGallery()
            } else if (text == "kamera") {
                startCamera()
            }
        }
    }

    private fun saveImage(context: Context, imageUri: Uri): Uri? {
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(imageUri)
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"

        var outputStream: OutputStream? = null
        var savedImageUri: Uri? = null

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                savedImageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                outputStream = savedImageUri?.let { contentResolver.openOutputStream(it) }
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                val imageFile = File(imagesDir, fileName)
                outputStream = FileOutputStream(imageFile)
                savedImageUri = Uri.fromFile(imageFile)
            }

            outputStream.use { output ->
                if (output != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                }
            }
            Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
        }

        return savedImageUri
    }

}