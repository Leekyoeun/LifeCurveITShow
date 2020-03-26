package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RegisterButton.setOnClickListener{
            val nextIntent= Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }
}
