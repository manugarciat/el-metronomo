package com.mnlgt.elmetronomo.ui

import com.mnlgt.elmetronomo.data.tempoInicial

data class MetronomoUiState(
    var bpm: Float = tempoInicial,
    var andando: Boolean = false,
    var acento: Int = 0,
    var subdivision: Int = 1
)