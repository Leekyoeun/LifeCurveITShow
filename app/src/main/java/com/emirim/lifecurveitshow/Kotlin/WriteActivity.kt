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

        supportActionBar?.title="글쓰기"

        //recyclerView에서 사용할 레이아웃 매니저를 생성
        val layoutManager=LinearLayoutManager(this@WriteActivity)
        //recyclerView를 횡으로 스크롤 할것이므로 layoutManager의 방향을 horizontal로 설정
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL

        //recyclerView에 레이아웃 매니저를 방금 생성한 layoutManager로 설정
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=MyAdapter()

        sendButton.setOnClickListener {
            if(TextUtils.isEmpty(input.text)){
                Toast.makeText(applicationContext,"메세지를 입력하세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val post=Post()
            val newRef=FirebaseDatabase.getInstance().getReference("Posts").push()
            post.writeTime= ServerValue.TIMESTAMP
            post.bguri=bgList[currentBgPosition]
            post.message=input.text.toString()
            post.writerId=getMyId()
            newRef.setValue(post)
            //저장성공 토스트 알림을 보여주고 activity 종료
            Toast.makeText(applicationContext,"공유되었습니다",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    //ID 인식
    fun getMyId(): String{
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView=itemView.imageView
    }
    inner class MyAdapter: RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(this@WriteActivity).inflate(R.layout.card_background,parent, false))
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
