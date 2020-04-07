package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_choose_category.*

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
