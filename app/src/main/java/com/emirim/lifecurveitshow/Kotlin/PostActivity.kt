package com.emirim.lifecurveitshow.Kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emirim.lifecurveitshow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    //로그에 TAG로 사용할 문자열
    val TAG="PostActivity"

    //파이어베이스의 test 키를 가진 데이터의 참조 객체를 가져온다
    val ref= FirebaseDatabase.getInstance().getReference("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //값의 변경이 있는 경우의 이벤트 리스너를 추가
        ref.addValueEventListener(object: ValueEventListener{
            //데이터 권한이 없는 경우
            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val message=snapshot.value.toString()
                //읽은 문자열을 로깅
                Log.d(TAG, message)
                //Firebase에서 전달받은 메세지로 제목을 변경
                supportActionBar?.title=message
            }
        })
        floatingActionButton.setOnClickListener{
            val intent= Intent(this@PostActivity, WriteActivity::class.java)
            startActivity(intent)
        }
    }
}
