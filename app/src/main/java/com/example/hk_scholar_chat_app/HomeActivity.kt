package com.example.hk_scholar_chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hk_scholar_chat_app.ui.theme.HkscholarchatappTheme
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.InitializationState
import io.getstream.chat.android.models.UploadAttachmentsNetworkType
import io.getstream.chat.android.models.User
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.extensions.watchChannelAsState
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.UUID

class HomeActivity : ComponentActivity() {

    // Define the CoroutineScope at the class level
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        // 1 - Set up the OfflinePlugin for offline storage
        val offlinePluginFactory = StreamOfflinePluginFactory(appContext = applicationContext)
        val statePluginFactory =
            StreamStatePluginFactory(config = StatePluginConfig(), appContext = this)

        // 2 - Set up the client for API calls and with the plugin for offline storage
        // eto ung api key talaga natin. wala pang laman
        val client = ChatClient.Builder(getString(R.string.api_key), applicationContext)
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()

        // eto ung sample api key from https://getstream.io/tutorials/android-chat/
        // uncomment this and icomment ung nasa taas para matest nyo
//        val client = ChatClient.Builder("uun7ywwamhs9", applicationContext)
//            .withPlugins(offlinePluginFactory, statePluginFactory)
//            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
//            .build()

        // 3 - Authenticate and connect the user
        val user = User(
            id = "tutorial-droid",
            name = "Tutorial Droid",
            image = "https://bit.ly/2TIt8NR"
        )
        client.connectUser(
            user = user,
            // TODO: change this hardcoded token to something else later
            // PAANO KUMUHA NG TOKEN
            // https://getstream.io/chat/docs/php/token_generator/
            // secret: b243swcntu8db56swfvx3cnqp4pkje9cqpkwmw29hcp6b232egw2n3bp5yg22k4x
            // tutorial-droid token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.WwfBzU1GZr0brt_fXnqKdKhz3oj0rbDUm2DqJO_SS5U
            // Ken22 token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiS2VuMjIifQ.T7ZSz0dRDdosXz0HR49V3GswsRY8B4OgHpX0q3-ZCZU
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.WwfBzU1GZr0brt_fXnqKdKhz3oj0rbDUm2DqJO_SS5U"
        ).enqueue() { result ->

            if (result.isSuccess) {
                // TODO: Handle success LATER NA TO
            } else {
                // TODO: Handle error LATER NA TO
                val error = result.errorOrNull()
                println("Failed to connect user: ${error?.message ?: "Unknown error"}")
            }

            /**
             * CODE FROM https://getstream.io/chat/docs/android/?language=kotlin#channels
             */
            val channelClient = client.channel(channelType = "messaging", channelId = "travel")
            val extraData = mutableMapOf<String, Any>(
                "name" to "Awesome channel about traveling"
            )

            // Creating a channel with the low level client
            channelClient.create(memberIds = emptyList(), extraData = extraData).enqueue { result ->
                if (result.isSuccess) {
                    val channel: Channel = result.getOrThrow()
                    println("channel creation success")
                    // Use channel by calling methods on channelClient
                } else {
                    // Handle result.error()
                }
            }

            setContent {
                val context = LocalContext.current
                val clientInitialisationState by client.clientState.initializationState.collectAsState()


                ChatTheme {
                    Scaffold(
                        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier.padding(
                                PaddingValues(
                                    top = paddingValues.calculateTopPadding(),
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 0.dp
                                )
                            )
                        ) {
                            when (clientInitialisationState) {
                                InitializationState.COMPLETE -> {
                                    ChannelsScreen(
                                        title = stringResource(id = R.string.app_name),
                                        isShowingHeader = true,
                                        onChannelClick = { channel ->
                                            startActivity(
                                                ChannelActivity.getIntent(
                                                    context,
                                                    channel.cid
                                                )
                                            )
                                        },
                                        onHeaderActionClick = {
                                            println("Header Action Click")
                                            // NOTE: This will create a new channel
//                                            val channelClient = client.channel(channelType = "messaging", channelId = "kenTest")
//
//                                            channelClient.create(memberIds = emptyList(), extraData = emptyMap()).enqueue { result ->
//                                                if (result.isSuccess) {
//                                                    val newChannel: Channel = result.getOrThrow()
//                                                } else {
//                                                    // Handle result.error()
//                                                }
//                                            }

                                        },
                                        onBackPressed = { finish() }
                                    )

                                }




                                InitializationState.INITIALIZING -> {
                                    androidx.compose.material.Text(text = "Initialising...")
                                }

                                InitializationState.NOT_INITIALIZED -> {
                                    androidx.compose.material.Text(text = "Not initialized...")
                                }
                            }
                        }
                    }
                }

            }


        }
    }
}
