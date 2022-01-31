package com.shusharin.songbase

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.shusharin.songbase.core.SongBaseApp
import com.shusharin.songbase.ui.MainViewModel
import com.shusharin.songbase.ui.SongAdapter

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: SongAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = (application as SongBaseApp).viewModel
        adapter = SongAdapter(object : SongAdapter.Retry {
            override fun tryAgain() {
                viewModel.findSongs()
            }
        })
        setupRecyclerView()
        checkPermission()

        viewModel.observe(this, {
            adapter.update(it)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST ->
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            PERMISSION_READ) == PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, "Разрешение получено!", Toast.LENGTH_SHORT).show()
                        viewModel.findSongs()
                    }
                } else {
                    Toast.makeText(this, "Нет разрешения", Toast.LENGTH_SHORT).show()
                    finish()
                }

        }

    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                PERMISSION_READ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    PERMISSION_READ)
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(PERMISSION_READ), MY_PERMISSION_REQUEST)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(PERMISSION_READ), MY_PERMISSION_REQUEST)
            }
        } else {
            viewModel.findSongs()
        }
    }

    companion object {
        val PERMISSION_READ = Manifest.permission.READ_EXTERNAL_STORAGE
        val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
        val MY_PERMISSION_REQUEST = 123
    }
}