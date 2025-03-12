package com.example.hk_scholar_chat_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hk_scholar_chat_app.sealed_screens.AuthScreens
import com.example.hk_scholar_chat_app.views.LoginScreen

@Composable
fun SetRootNavGraph(navController: NavHostController){
    NavHost(
        startDestination = AuthScreens.Login.route,
        navController = navController
    ) {
        composable(route = AuthScreens.Login.route) {
            LoginScreen()
        }
    }
}