package com.emirim.lifecurveitshow.Kotlin

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.emirim.lifecurveitshow.R

class MainActivity : AppCompatActivity() {

    private val RECORD_REQUEST_CODE = 1000
    private val TAG = "PermissionDemo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupPermissions()
        
    }

    private fun setupPermissions() {
        //스토리지 읽기 퍼미션을 permission 변수에 담는다
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0]  != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
                } else {
                    getAllPhotos()
                }
                return
            }
        }
    }

    private fun getAllPhotos() {
        val cursor = getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
            }
            cursor.close()
        }
    }
}
