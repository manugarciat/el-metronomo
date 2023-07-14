package com.mnlgt.elmetronomo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Metronomo(private val metronomoAudioTrack: MetronomoAudioTrack) {

    private var tempo:Float = 60F

    suspend fun iniciarTrack(){
        metronomoAudioTrack.iniciar()
    }

    fun setTempo(t: Float) {
        tempo = t
        metronomoAudioTrack.setTempo(t)
    }
}