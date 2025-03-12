package com.example.hk_scholar_chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        // 1 - Set up the OfflinePlugin for offline storage
        val offlinePluginFactory = StreamOfflinePluginFactory(
            appContext = applicationContext
        )
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
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.WwfBzU1GZr0brt_fXnqKdKhz3oj0rbDUm2DqJO_SS5U"
        ).enqueue() { result ->
            /**
             * CODE FROM https://getstream.io/chat/docs/android/?language=kotlin#channels
             */
            if (result.isSuccess) {
                // TODO: Handle success LATER NA TO
            } else {
                // TODO: Handle error LATER NA TO
                println("Failed to connect user: ${result.getOrThrow() ?: "Unknown error"}")
            }

            setContent {
                // Observe the client connection state
                val clientInitialisationState by client.clientState.initializationState.collectAsState()

                ChatTheme {
                    when (clientInitialisationState) {
                        InitializationState.COMPLETE -> {
                            ChannelsScreen(
                                title = stringResource(id = R.string.app_name),
                                isShowingHeader = true,
                                onChannelClick = { channel ->
                                    startActivity(ChannelActivity.getIntent(this, channel.cid))
                                },
                                onHeaderActionClick = {
                                    println("Header Action Click")
                                    // Create a new channel when header action is clicked
                                    val randomId = UUID.randomUUID().toString().substring(0, 8)
                                    val channelClient = client.channel(channelType = "messaging", channelId = randomId)
                                    val extraData = mutableMapOf(
                                        "name" to "New Channel ${randomId}"
                                    )

                                    channelClient.create(memberIds = emptyList(), extraData = extraData).enqueue { channelResult ->
                                        if (channelResult.isSuccess) {
                                            val channel: Channel = channelResult.getOrThrow()
                                            println("Successfully created channel: ${channel.cid}")
                                            // startActivity(ChannelActivity.getIntent(this, channel.cid))
                                        } else {
                                            println("Failed to create channel: ${channelResult.errorOrNull()?.message ?: "Unknown error"}")
                                        }
                                    }
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
