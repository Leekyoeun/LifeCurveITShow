package com.emirim.lifecurveitshow.Kotlin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        sendButton.setOnClickListener {
            if (TextUtils.isEmpty(input.text)) {
                Toast.makeText(applicationContext, "메세지를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}