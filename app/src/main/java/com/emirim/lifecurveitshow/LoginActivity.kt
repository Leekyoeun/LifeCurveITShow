package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

            LoginButton.setOnClickListener {
                startActivity(Intent(this, ChooseGrapicsActivity::class.java))
                //DB에서 아이디랑 비밀번호 체크하는 부분
            }
        }
    }
}
