package com.emirim.lifecurveitshow.Kotlin.Login.logic

import com.emirim.lifecurveitshow.Kotlin.Login.model.AuthCredentials

interface ISignInBL {
    fun checkUserCredentials(credentials: AuthCredentials): Boolean

}