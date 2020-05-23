package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class FacebookLoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        facebooklogin.setOnClickListener {
            facebookLogin()
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
        }
    }
    private fun facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                //페이스북 로그인 성공
                handleFacebookAccessToken(result?.accessToken)
                //startActivity((this, ChooseGrapicsActivity::class.java))
                Toast.makeText(applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
            }
            override fun onCancel() {
                //페이스북 로그인 취소
                //updateUI(null)
            }

            override fun onError(error: FacebookException?) {
                //페이스북 로그인 실패
                //updateUI(null)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        //onActivityResult에서는 callbackManager에 로그인 결과를 넘겨줍니다
        //여기에 callbackManager?.onAcitivyResult가 있어야 onSuccess를 호출할 수 있습니다.
    }

    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d("MainActivity", "handleFacebookAccessToken:$token")
        if (token != null) {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MainActivity", "signInWithCredential:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                        Toast.makeText(applicationContext,"Authentication failed.",Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onStart() { //로그인유저되있는 유저를 확인함
        super.onStart()
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    /*private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            status.text = user.displayName
            detail.text = user.photoUrl.toString()
        } else {
            status.setText("로그인 안됨")
            detail.text = "photoURL"
        }
    }*/

}
