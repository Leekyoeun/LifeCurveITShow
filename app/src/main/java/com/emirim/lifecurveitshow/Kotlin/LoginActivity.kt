package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_login.*
import me.aflak.libraries.FingerprintCallback
import me.aflak.libraries.FingerprintDialog


class LoginActivity : AppCompatActivity() {


    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    //private const val TAG="GoogleActivity"
    private val RC_SIGN_IN = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        LoginButton.setOnClickListener {
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
        }
        button.setOnClickListener {
            FingerprintDialog.initialize(this)
                .title("지문인증")
                .message("지문으로 인증합니다")
                .callback(object: FingerprintCallback{
                    override fun onAuthenticationSuccess() {
                        Toast.makeText(applicationContext,"인증 성공",Toast.LENGTH_SHORT).show()
                    }
                    override fun onAuthenticationCancel() {
                        Toast.makeText(applicationContext,"인증 실패",Toast.LENGTH_SHORT).show()
                    }
                })
                .show();8
        }
    }
}