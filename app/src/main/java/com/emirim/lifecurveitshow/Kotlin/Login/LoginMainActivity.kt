package com.emirim.lifecurveitshow.Kotlin.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.emirim.lifecurveitshow.Kotlin.ChooseCategory
import com.emirim.lifecurveitshow.Kotlin.FacebookLoginActivity
import com.emirim.lifecurveitshow.Kotlin.GoogleLoginActivity
import com.emirim.lifecurveitshow.Kotlin.Register.RegisterActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_login.*
import me.aflak.libraries.FingerprintCallback
import me.aflak.libraries.FingerprintDialog

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
            button.setOnClickListener {

           FingerprintDialog.initialize(this)
               .title("지문인증")
               .message("지문으로 인증합니다")
                .callback(object: FingerprintCallback{
                   override fun onAuthenticationSuccess() {
                       Toast.makeText(applicationContext,"인증 성공", Toast.LENGTH_SHORT).show()
                       //startActivity(Intent(this, ChooseCategory::class.java))
                   }

                   override fun onAuthenticationCancel() {
                       Toast.makeText(applicationContext,"인증 실패",Toast.LENGTH_SHORT).show()
                   }
               })
               .show()
        }

    }
}
