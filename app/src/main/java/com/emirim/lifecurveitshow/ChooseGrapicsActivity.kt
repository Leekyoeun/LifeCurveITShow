package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_grapics.*

class ChooseGrapicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)

        //카테고리 선택 버튼을 눌렀을 때
        choosecategoryButton.setOnClickListener(){
            startActivity(Intent(this, ChooseCategory::class.java))
        }

        //직접 그리기 버튼 눌렀을 때
        chooseUserGrapics.setOnClickListener {
            startActivity(Intent(this, ChooseMyDraw::class.java))
        }
    }
}
