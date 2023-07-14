package com.mnlgt.elmetronomo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.mnlgt.elmetronomo.ui.MetronomoScreen
import com.mnlgt.elmetronomo.ui.MetronomoViewModel
import com.mnlgt.elmetronomo.ui.MetronomoViewModelFactory
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val metronomoAudioTrack = MetronomoAudioTrack()
        val metronomo = Metronomo(metronomoAudioTrack)

        val viewModelFactory = MetronomoViewModelFactory(metronomo)
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
                    MetronomoScreen(Modifier, viewModel)
                }
            }
        }
    }
}

