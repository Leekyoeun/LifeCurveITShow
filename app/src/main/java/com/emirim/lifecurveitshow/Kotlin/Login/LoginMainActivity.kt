package com.emirim.lifecurveitshow.Kotlin.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.emirim.lifecurveitshow.Kotlin.FacebookLoginActivity
import com.emirim.lifecurveitshow.Kotlin.GoogleLoginActivity
import com.emirim.lifecurveitshow.Kotlin.Login.logic.ISignInBL
import com.emirim.lifecurveitshow.Kotlin.Login.logic.SignInBL
import com.emirim.lifecurveitshow.Kotlin.Login.model.AuthCredentials
import com.emirim.lifecurveitshow.Kotlin.Register.RegisterActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_login.*
import me.aflak.libraries.FingerprintCallback
import me.aflak.libraries.FingerprintDialog
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class LoginMainActivity : AppCompatActivity() {
    private val signInBL: ISignInBL = SignInBL()
    private lateinit var signInUI: SignInUI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SignInUI().setContentView(this)

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
        button.setOnClickListener {
            FingerprintDialog.initialize(this)
                .title("지문인증")
                .message("지문으로 인증합니다")
                .callback(object : FingerprintCallback {
                    override fun onAuthenticationSuccess() {
                        Toast.makeText(applicationContext, "인증 성공", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationCancel() {
                        Toast.makeText(applicationContext, "인증 실패", Toast.LENGTH_SHORT).show()
                    }
                })
                .show()
        }
    }
    fun authorizeUser(username: String, password: String){
        doAsync {
            val authorized=signInBL.checkUserCredentials(
                AuthCredentials(username=username, password = password)
            )
            activityUiThread {
                if(authorized) toast("Signed!")
                else signInUI.showAccessDeniedAlertDialog()
            }
        }
    }
}
