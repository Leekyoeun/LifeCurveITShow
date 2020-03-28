package com.emirim.lifecurveitshow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)  // 실제 사용할 메인 액티비티
        startActivity(intent)
        finish()
    }

}
