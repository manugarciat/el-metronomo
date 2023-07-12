package com.mnlgt.elmetronomo

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Metronomo(private val audioManager: AudioManager) {

    suspend fun iniciar() {

        val delayNanos: Long = 1000000000
        var wakeup = System.nanoTime() + delayNanos //Half second from right now

        //soundP.play(tick, 1F, 1F, 1, 0, 1F)

        audioManager.playTick()
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
                //soundP.play(tick, 1F, 1F, 1, 0, 1F)
                audioManager.playTick()
                wakeup += delayNanos
            }
            //}
        }

    }

}