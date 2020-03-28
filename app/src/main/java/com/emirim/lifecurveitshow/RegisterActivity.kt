package com.emirim.lifecurveitshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        OKButton.setOnClickListener{
            startActivity(Intent(this, ChooseGrapicsActivity::class.java))
            //여기서 아이디 중복 체크랑 DB에 집어넣는 작업하면 됌
            Toast.makeText(this,"회원가입 되셨습니다",Toast.LENGTH_SHORT).show()
        }
    }
}
