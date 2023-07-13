package com.mnlgt.elmetronomo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Metronomo(private val metronomoAudioManager: MetronomoAudioManager, private val metronomoAudioTrack: MetronomoAudioTrack) {

    private var delayNanos: Long = 1000000000
    private var tempo:Int = 60

    suspend fun iniciar() {

        var wakeup = System.nanoTime() + delayNanos //Half second from right now

        metronomoAudioManager.playTick()

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

                metronomoAudioManager.playTick()
                wakeup += delayNanos
            }
        }
    }

    suspend fun iniciarTrack(){
        metronomoAudioTrack.iniciar()
        //iniciar()
    }

    fun setTempo(t: Float) {
        tempo = t.toInt()
        delayNanos = ((60 / tempo) * 1000000000).toLong()
        metronomoAudioTrack.setTempo(t.toInt())
    }
}