package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emirim.lifecurveitshow.Kotlin.Chart.MPChartActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_category.*

class ChooseCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        Graphic1.setOnClickListener {
            startActivity(Intent(this, MPChartActivity::class.java))
        }
    }
}
