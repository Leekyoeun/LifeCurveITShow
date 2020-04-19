package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
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

        RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        LoginButton.setOnClickListener {
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
        }
        googlelogin.setOnClickListener { signIn() }

        //Google 로그인 옵션 구성
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            toMainActivity(firebaseAuth.currentUser)
        }
    }//end

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign 성공
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                //Google Sign 실패
                Log.w("LoginActivity", "구글 로그인 실패", e)
            }
        }
    }//end

    //firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 FirebaseAuth로 교환하고 Firebase에 연동
        val crdential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(crdential)
            .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                        toMainActivity(firebaseAuth?.currentUser)
                    } else {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                        Snackbar.make(googlelogin, "로그인에 실패", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }//End

        //toMainActivity
        fun toMainActivity(user: FirebaseUser?) {
            if(user!=null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }
    }//end

    //signIn
    private fun signIn(){
        val signIntent=googleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }//end

    fun onClick(){

    }

    private fun signOut(){ //로그아웃
        //Firebase sign out
        firebaseAuth.signOut()

        googleSignInClient.signOut().addOnCompleteListener(this){
            //updateUI
        }
    }
    private fun revokeAccess(){ //회원 탈퇴
        firebaseAuth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(this){

        }
    }//Google Login 끝
}