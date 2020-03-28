package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_grapics.*

class ChooseGrapicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_grapics)

        choosecategory.setOnClickListener {
            startActivity(Intent(this, ChooseCategory::class.java))
        }
        chooseUserGrapics.setOnClickListener {
            startActivity(Intent(this, ChooseUserGrapics::class.java))
        }
    }
}
