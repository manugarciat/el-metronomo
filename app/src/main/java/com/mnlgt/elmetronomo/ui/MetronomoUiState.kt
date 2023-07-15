package com.mnlgt.elmetronomo.ui

import com.mnlgt.elmetronomo.data.acentoInicial
import com.mnlgt.elmetronomo.data.subDivInicial
import com.mnlgt.elmetronomo.data.tempoInicial

data class MetronomoUiState(
    var bpm: Float = tempoInicial,
    var andando: Boolean = false,
    var acento: Int = acentoInicial,
    var subdivision: Int = subDivInicial
)