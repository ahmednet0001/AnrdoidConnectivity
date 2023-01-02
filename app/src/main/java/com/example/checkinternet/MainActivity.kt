package com.example.checkinternet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.checkinternet.connection.ConnectionState
import com.example.checkinternet.connection.currentConnectivityState
import com.example.checkinternet.connection.observeConnectivityAsFlow
import com.example.checkinternet.ui.theme.CheckinternetTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckinternetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   Column() {
                       ConnectivityStatus()
                   }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collect {
            value=it
        }
    }
}



@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    // This will cause re-composition on every network state change
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
      Greeting(name = "Ahmed")
    } else {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp),
            backgroundColor = Color.LightGray,
            color = Color.Red //progress color
        )
        // Show UI for No Internet Connectivity
    }
}