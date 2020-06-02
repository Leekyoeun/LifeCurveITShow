package com.emirim.lifecurveitshow.Kotlin.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.emirim.lifecurveitshow.Kotlin.FacebookLoginActivity
import com.emirim.lifecurveitshow.Kotlin.GoogleLoginActivity
import com.emirim.lifecurveitshow.Kotlin.Register.RegisterActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        facebooklogin.setOnClickListener {
            startActivity(Intent(this, FacebookLoginActivity::class.java))
        }
        googlelogin.setOnClickListener {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
        }
        RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        LoginButton.setOnClickListener {
            startActivity(Intent(this, LoginMainActivity::class.java))
            Toast.makeText(applicationContext, "로그인 되었습니다", Toast.LENGTH_SHORT).show()
        }

    }
}
