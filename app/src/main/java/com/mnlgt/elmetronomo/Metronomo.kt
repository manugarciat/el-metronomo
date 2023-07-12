package com.mnlgt.elmetronomo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Metronomo(private val audioManager: AudioManager) {

    private var delayNanos: Long = 1000000000

    suspend fun iniciar() {

        var wakeup = System.nanoTime() + delayNanos //Half second from right now

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

                audioManager.playTick()
                wakeup += delayNanos
            }
        }
    }

    fun setTempo(tempo: Float) {
        delayNanos = ((60 / tempo) * 1000000000).toLong()
    }
}