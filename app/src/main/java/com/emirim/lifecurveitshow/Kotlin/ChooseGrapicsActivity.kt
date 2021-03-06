package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_grapics.*
import org.goldenage.com.goldenage.MainActivity

class ChooseGrapicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)

        //카테고리 선택을 눌렀을 때
        choosecategoryButton. setOnClickListener{
            val nextIntent=Intent(this,ChooseCategory::class.java)
            startActivity(nextIntent)
        }
        //직접 그리는 것을 선택했을 때
        chooseUserGrapics.setOnClickListener {
            val nextIntent=Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }
}
