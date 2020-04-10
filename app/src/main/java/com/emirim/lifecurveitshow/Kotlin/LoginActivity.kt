package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.emirim.lifecurveitshow.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    //private const val TAG="GoogleActivity"
    private val RC_SIGN_IN = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googlelogin.setOnClickListener { signIn() }

        //구글 로그인 옵션 구성 email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()

        RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        LoginButton.setOnClickListener {
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
            //DB에서 아이디랑 비밀번호 체크하는 부분

        }

    }

    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) { //이미 로그인 되어있을시 바로 메일 엑티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    }//onstart end

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (a: ApiException) {
                Log.w("LoginActivity", "로그인 실패")
            }
        }
    }

    //firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle: " + acct.id!!)

        //Google SignInAccount 객체에서 ID를 가져와서 FirebaseAuth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                toMainActivity(firebaseAuth?.currentUser)
            } else {
                Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                Snackbar.make(googlelogin, " 로그인에 실패하였습니다", Snackbar.LENGTH_SHORT).show()
            }
        }
    }//firebaseWithGoogle 끝

    fun toMainActivity(user: FirebaseUser?) {
        if (user != null) { //MainActivity로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }//toMainActivity 끝

    //Sign In
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}