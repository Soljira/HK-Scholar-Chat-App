package com.example.hk_scholar_chat_app.views

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import com.example.hk_scholar_chat_app.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hk_scholar_chat_app.HomeActivity
import com.example.hk_scholar_chat_app.ui.theme.HkscholarchatappTheme

@Composable
fun LoginScreen(){
    HkscholarchatappTheme {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo()
                CardForLogin(
                    username = username,
                    password = password,
                    onUsernameChange = { username = it },
                    onPasswordChange = { password = it }
                )


            }

        }
    }

}



@Composable
fun SigninButton(onClick:() -> Unit) {
    Button(
        modifier = Modifier
            .width(310.dp)
            .padding(10.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.small
    ) {
        Text(text = "Sign in")
    }
}


@Composable
fun CardForLogin(username:String, password:String, onUsernameChange:(String)->Unit, onPasswordChange:(String)->Unit){
    var isPasswordVisible by remember { mutableStateOf(false) }
    var icon = if (isPasswordVisible) painterResource(id = R.drawable.open_eye) else painterResource(id = R.drawable.close_eye)
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(15.dp)
            .width(350.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large

    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .width(280.dp),
                text = "Sign in",
                style = MaterialTheme.typography.headlineSmall,
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(280.dp),
                value = username,
                onValueChange = onUsernameChange,
                label = { Text(text = "Username") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(280.dp),
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(text = "Password") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = "visibility"
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            SigninButton(
                onClick = {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
            )
            ForgotPassword(
                onPasswordClick = {

                }
            )
            Spacer(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            CreateAccountButton(
                onCLick = {

                }
            )



        }

    }

}

@Composable
fun CreateAccountButton(onCLick : () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .padding(10.dp)
            .width(310.dp),
        onClick = onCLick
    ) {
        Text(text = "Create Account")
    }
}

@Composable
fun ForgotPassword(onPasswordClick: () -> Unit) {
    Text(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onPasswordClick
            ),
        text = "forgot password",
        style = MaterialTheme.typography.labelMedium,
        textAlign = TextAlign.Start
    )
}

@Composable
fun Logo(modifier: Modifier = Modifier){
    Text(
        text = "Hawak Kamay",
        style = MaterialTheme.typography.headlineLarge,
        color = Color.Black
    )
    Text(
        text = "Scholarship",
        style = MaterialTheme.typography.headlineLarge,
        color = Color.Black
    )
    Text(
        text = "CHAT APP",
        style = MaterialTheme.typography.headlineSmall,
        color = Color.Black
    )
    Text(
        modifier = Modifier
            .padding(vertical = 20.dp),
        text = "Making Lives Better Through Education",
        style = MaterialTheme.typography.labelMedium,
        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}