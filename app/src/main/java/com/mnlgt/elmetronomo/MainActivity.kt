package com.mnlgt.elmetronomo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.mnlgt.elmetronomo.ui.MetronomoScreen
import com.mnlgt.elmetronomo.ui.MetronomoViewModel
import com.mnlgt.elmetronomo.ui.MetronomoViewModelFactory
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val metronomoAudioTrack = MetronomoAudioTrack()

        val viewModelFactory = MetronomoViewModelFactory(metronomoAudioTrack)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[MetronomoViewModel::class.java]

        super.onCreate(savedInstanceState)

        setContent {
            ElMetronomoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    MetronomoScreen(viewModel, windowSize.widthSizeClass)
                }
            }
        }
    }
}

