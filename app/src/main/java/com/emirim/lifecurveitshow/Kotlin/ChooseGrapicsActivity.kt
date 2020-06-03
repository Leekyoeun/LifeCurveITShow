package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_grapics.*
import org.goldenage.com.goldenage.MainActivity

class ChooseGrapicsActivity : AppCompatActivity() {
    val Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)

        //카테고리 선택 버튼을 눌렀을 때
        /*choosecategoryButton.setOnClickListener{
            startActivity(Intent(this, com.emirim.lifecurveitshow.Kotlin.MainActivity::class.java))
        }*/
        //직접 그리기 선택 버튼을 클릭했을 때
        chooseUserGrapics.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
