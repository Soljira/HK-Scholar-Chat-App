package com.example.hk_scholar_chat_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.hk_scholar_chat_app.sealed_screens.AuthScreens

@Composable
fun SetRootNavGraph(navController: NavHostController){
    NavHost(
        startDestination = AuthScreens.Login.route,
        navController = navController
    ){

    }
}