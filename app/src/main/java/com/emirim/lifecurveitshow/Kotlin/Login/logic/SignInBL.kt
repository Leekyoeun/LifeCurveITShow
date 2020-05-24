package com.emirim.lifecurveitshow.Kotlin.Login.logic

import com.emirim.lifecurveitshow.Kotlin.Login.model.AuthCredentials

class SignInBL: ISignInBL{
    override fun checkUserCredentials(auth: AuthCredentials): Boolean {
        return ("user"==auth.username && "pass"==auth.password)
    }
}