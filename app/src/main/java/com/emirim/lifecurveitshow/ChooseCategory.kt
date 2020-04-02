package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_category.*
import kotlinx.android.synthetic.main.activity_choose_grapics.*

class ChooseCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        Graphic1.setOnClickListener{
            startActivity(Intent(this, PostActivity::class.java))
        }
        Graphic2.setOnClickListener{
            startActivity(Intent(this, PostActivity::class.java))
        }
        Graphic3.setOnClickListener{
            startActivity(Intent(this, PostActivity::class.java))
        }
    }
}
