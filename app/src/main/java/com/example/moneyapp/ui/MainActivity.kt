package com.example.moneyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Uang Masuk"

        // Memulihkan visibilitas jika ada
        if (savedInstanceState != null) {
            binding.btDaftar.visibility = savedInstanceState.getInt("BT_DAFTAR_VISIBILITY", View.VISIBLE)
        }

        binding.btDaftar.setOnClickListener {
            binding.btDaftar.visibility = View.GONE
            val fragmentManager = supportFragmentManager
            val daftarUangMasukFragment = DaftarUangMasukFragment()
            val fragment = fragmentManager.findFragmentByTag(DaftarUangMasukFragment::class.java.simpleName)
            if (fragment !is DaftarUangMasukFragment) {
                //Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
                fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, daftarUangMasukFragment, DaftarUangMasukFragment::class.java.simpleName)
                    .commit()
            }
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("BT_DAFTAR_VISIBILITY", binding.btDaftar.visibility)
    }
}