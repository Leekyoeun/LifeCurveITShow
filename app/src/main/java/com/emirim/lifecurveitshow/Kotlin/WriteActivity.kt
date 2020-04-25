package com.emirim.lifecurveitshow.Kotlin

import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emirim.lifecurveitshow.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.card_background.view.*

class WriteActivity : AppCompatActivity() {
    var currentBgPosition = 0

    //글쓰기 모드를 저장하는 변수
    var mode = "post"

    //댓글쓰기인 경우 글의 ID
    var postId = ""

    /**
     * 배경 리스트 데이터
     * res/drawable 디렉토리에 있는 배경 이미지를 uri 주소로 사용한다.
     * uri 주소로 사용하면 추후 웹에 있는 이미지 URL 도 바로 사용이 가능하다.
     */
    val bgList = mutableListOf(
        "android.resource://com.akj.anonymoussns/drawable/default_bg"
        , "android.resource://com.akj.anonymoussns/drawable/bg2"
        , "android.resource://com.akj.anonymoussns/drawable/bg3"
        , "android.resource://com.akj.anonymoussns/drawable/bg4"
        , "android.resource://com.akj.anonymoussns/drawable/bg5"
        , "android.resource://com.akj.anonymoussns/drawable/bg6"
        , "android.resource://com.akj.anonymoussns/drawable/bg7"
        , "android.resource://com.akj.anonymoussns/drawable/bg8"
        , "android.resource://com.akj.anonymoussns/drawable/bg9"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        //전달 받은 intent에서 댓글모드인지 확인
        intent.getStringExtra("mode")?.let {
            mode = intent.getStringExtra("mode")
            postId = intent.getStringExtra("postId")
        }
        supportActionBar?.title = if (mode == "post") "글쓰기" else "댓글쓰기"

        //recyclerView에서 사용할 레이아웃 매니저를 생성
        val layoutManager = LinearLayoutManager(this@WriteActivity)
        //recyclerView를 횡으로 스크롤 할것이므로 layoutManager의 방향을 horizontal로 설정
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        //recyclerView에 레이아웃 매니저를 방금 생성한 layoutManager로 설정
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MyAdapter()

        sendButton.setOnClickListener {
            if (TextUtils.isEmpty(input.text)) {
                Toast.makeText(applicationContext, "메세지를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (mode == "post") {
                //Post 객체 생성
                val post = Post()
                //Firebase의 Posts 참조에서 객체를 저장하기 위한 새로운 키를 생성하고 참조를 newRef에 저장
                val newRef = FirebaseDatabase.getInstance().getReference("Posts").push()
                //글이 쓰여진 시간은 Firebase 서버의 시간으로 설정
                post.writeTime = ServerValue.TIMESTAMP
                //배경 Uri 주소를 현재 선택된 배경의 주소로 할당
                post.bguri = bgList[currentBgPosition]
                //메세지는 input EditText의 텍스트 내용을 할당
                post.message = input.text.toString()
                //글쓴 사람의 ID는 디바이스의 아이디로 할당
                post.writerId = getMyId()
                //글의 ID는 새로 생성된 파이어베이스 참조의 키로 할당
                post.postId = newRef.key.toString()
                //Post 객체를 새로 생성한 참조에 저장
                newRef.setValue(post)
                //저장성공 토스트 알림을 보여주고 Activity 종료
                Toast.makeText(applicationContext, "공유되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val comment = Comment()
                //Firebase의 Posts 참조에서 객체를 저장하기 위한 새로운 키를 생성하고 참조를 newRef에 저장
                val newRef = FirebaseDatabase.getInstance().getReference("Comments/$postId").push()
                comment.writeTime = ServerValue.TIMESTAMP
                comment.bguri = bgList[currentBgPosition]
                //메세지는 input Edittext의 텍스트 내용을 할당
                comment.message = input.text.toString()
                //글쓴 사람의 ID는 디바이스의 아이디로 할당
                comment.writerId = getMyId()
                //글의 ID는 새로 생성된 파이어베이스 참조의 키로 할당
                comment.commetId = newRef.key.toString()
                //댓글이 속한 글의 ID
                comment.postId = postId
                newRef.setValue(comment)
                //저장성공 토스트 알림을 보여주고 Activity 종료
                Toast.makeText(applicationContext, "공유되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }

            val post = Post()
            val newRef = FirebaseDatabase.getInstance().getReference("Posts").push()
            post.writeTime = ServerValue.TIMESTAMP
            post.bguri = bgList[currentBgPosition]
            post.message = input.text.toString()
            post.writerId = getMyId()
            newRef.setValue(post)
            //저장성공 토스트 알림을 보여주고 activity 종료
            Toast.makeText(applicationContext, "공유되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    //ID 인식
    fun getMyId(): String {
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.imageView
    }

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(this@WriteActivity)
                    .inflate(R.layout.card_background, parent, false)
            )
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
            return bgList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //이미지 로딩 라이브러리
            Picasso.get()
                .load(Uri.parse(bgList[position]))
                .fit()
                .centerCrop()
                .into(holder.imageView)

            holder.itemView.setOnClickListener {
                Picasso.get()
                    .load(Uri.parse(bgList[position]))
                    .fit()
                    .centerCrop()
                    .into(writeBackground)
            }
        }
    }
}
