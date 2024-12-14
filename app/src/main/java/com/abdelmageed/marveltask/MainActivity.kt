package com.abdelmageed.marveltask

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.abdelmageed.marveltask.extensions.isInternetAvailable
import com.abdelmageed.marveltask.ui.AppNavHost
import com.abdelmageed.marveltask.ui.theme.MarvelTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr
            ) {

                MarvelTaskTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        var isConnected by remember { mutableStateOf(isInternetAvailable()) }

                        DisposableEffect(this) {
                            val connectivityReceiver = object : BroadcastReceiver() {
                                override fun onReceive(context: Context?, intent: Intent?) {
                                    isConnected = isInternetAvailable()
                                }
                            }
                            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                            registerReceiver(connectivityReceiver, intentFilter)

                            onDispose {
                                unregisterReceiver(connectivityReceiver)
                            }
                        }
                        val navController = rememberNavController()
                        if (!isConnected) {
                            Toast.makeText(
                                this,
                                "There is not internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        AppNavHost(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            innerPadding = innerPadding,
                        )
                    }
                }
            }
        }
    }
}