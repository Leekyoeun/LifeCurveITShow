package com.emirim.lifecurveitshow.Kotlin.Login

import android.view.View
import com.emirim.lifecurveitshow.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignInUI: AnkoComponent<LoginMainActivity> {
        private lateinit var ankocontext: AnkoContext<LoginMainActivity>

        override fun createView(ui: AnkoContext<LoginMainActivity>): View=with(ui) {
            ankocontext = ui
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                val username = editText {
                    id = R.id.edit_username
                    textSize = 24f
                    hintResource = R.string.sign_in_username
                }.lparams(width = matchParent, height = wrapContent)
                val password = editText {
                    id = R.id.edit_password
                    textSize = 24f
                    hintResource = R.string.sign_in_password
                }.lparams(width = matchParent, height = wrapContent)
                button {
                    onClick { toast("Hello ${username.text}") }
                    id = R.id.btn_sign_in
                    textResource = R.string.sign_in_button
                    onClick {
                        handleOnSignIn(
                            ui = ui,
                            username = username.text.toString(),
                            password = password.text.toString()
                        )
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
        private fun handleOnSignIn(ui: AnkoContext<LoginMainActivity>, username: String, password: String){
            if(username.isBlank()|| password.isBlank()){
                with(ui){
                    alert(title= R.string.invaild_user_title, message=R.string.invalid_user_message){
                        positiveButton(R.string.button_close){}
                    }.show()
                }
            }else{
                ui.owner.authorizeUser(username,password)
            }
        }
        fun showAccessDeniedAlertDialog(){
            with(ankocontext){
                alert(title=R.string.access_denied_title, message=R.string.access_denied_msg){
                    positiveButton(R.string.button_close){}
                }.show()
            }
        }
    }