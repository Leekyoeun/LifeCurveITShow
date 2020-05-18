package com.emirim.lifecurveitshow.Kotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_grapics.*

class ChooseGrapicsActivity : AppCompatActivity() {
    val Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)


        //카테고리 선택 버튼을 눌렀을 때
        choosecategoryButton.setOnClickListener() {
            startActivity(Intent(this, ChooseCategory::class.java))
        }


        OPEN_GALLERY.setOnClickListener { loadImage() }
    }

    //이미지 불러오기
    private fun loadImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Gallery) {
            if (resultCode == Activity.RESULT_OK) {
                var dataUri = data?.data
                try {
                    var bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                } catch (e: Exception) {
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            } else {
                //something wrong
            }
        }
    }

}
