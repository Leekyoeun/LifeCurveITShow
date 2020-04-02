package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_my_draw.*

class ChooseMyDraw : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_my_draw)

        SaveButton.setOnClickListener {
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
        }
    }
}
