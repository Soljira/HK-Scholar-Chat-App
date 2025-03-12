package com.example.hk_scholar_chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hk_scholar_chat_app.ui.theme.HkscholarchatappTheme
import com.example.hk_scholar_chat_app.views.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HkscholarchatappTheme {
                LoginScreen()
            }
        }

    }
}
