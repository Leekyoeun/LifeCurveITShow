package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_grapics.*

class ChooseUserGraphics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        chooseUserGrapics.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
    }
}
