package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import kotlinx.android.synthetic.main.activity_login.*
import me.aflak.libraries.FingerprintCallback
import me.aflak.libraries.FingerprintDialog


class LoginActivity : AppCompatActivity() {

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
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
            Toast.makeText(applicationContext,"로그인 되었습니다",Toast.LENGTH_SHORT).show()
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
        //getHashKey()
    }

   /* private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }*/

}