package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_grapics.*
import org.goldenage.com.goldenage.ChartMainActivity

class ChooseGrapicsActivity : AppCompatActivity() {
    val Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)


        //직접 그리는 것을 선택했을 때
        chooseUserGrapics.setOnClickListener {
            startActivity(Intent(this, ChartMainActivity::class.java))
        }
    }
}
