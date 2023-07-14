package com.mnlgt.elmetronomo

import com.mnlgt.elmetronomo.data.acentoInicial
import com.mnlgt.elmetronomo.data.subDivInicial
import com.mnlgt.elmetronomo.data.tempoInicial

class Metronomo(private val metronomoAudioTrack: MetronomoAudioTrack) {

    //variables de metronomo
    private var _tempo: Float = tempoInicial
    private var _subdivision: Int = subDivInicial
    private var _acento: Int = acentoInicial

    suspend fun iniciarTrack(){
        metronomoAudioTrack.iniciar()
    }

    fun setTempo(t: Float) {
        _tempo = t
        metronomoAudioTrack.configMetronomo(t, _acento)
    }

    fun detener() {

    }

    fun setAcento(acento: Int) {

    }
}