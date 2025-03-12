package com.example.hk_scholar_chat_app.sealed_screens

sealed class AuthScreens(val route : String) {
    object Login: AuthScreens("sign_in_route")
}