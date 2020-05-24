package com.emirim.lifecurveitshow.Kotlin.Register


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.Kotlin.ChooseGrapicsActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //회원가입 소스 코드 삽입

        AuthButton.setOnClickListener {
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
        }

    }
}