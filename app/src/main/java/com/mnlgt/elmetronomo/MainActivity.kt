package com.mnlgt.elmetronomo

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mnlgt.elmetronomo.ui.MetronomoScreen
import com.mnlgt.elmetronomo.ui.MetronomoViewModel
import com.mnlgt.elmetronomo.ui.theme.ElMetronomoTheme
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val audioManager = AudioManager(this) //inicializar sonidos
        val metronomo = Metronomo(audioManager)
        val viewModel = MetronomoViewModel(metronomo)

        setContent {
            ElMetronomoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MetronomoScreen(Modifier, metronomo, viewModel)
                }
            }
        }
    }
}

suspend fun tictoc(soundP: SoundPool, tick: Int, tick2: Int) {

    val delayNanos: Long = 1000000000
    var wakeup = System.nanoTime() + delayNanos //Half second from right now
    soundP.play(tick, 1F, 1F, 1, 0, 1F)
    var now: Long

    while (true) {
        now = System.nanoTime()

        //If we are less than 50 milliseconds from wake up. Spin away.
        if (now <= wakeup - 50 * 1000000) {
            //Sleep in very small increments, so we don't spin unrestricted.
            withContext(Dispatchers.IO) {
                Thread.sleep(10)
            }
        }
        if (now >= wakeup) {
            //Play sound
            soundP.play(tick, 1F, 1F, 1, 0, 1F)
            wakeup += delayNanos
        }
        //}
    }
}

