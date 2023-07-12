package com.mnlgt.elmetronomo.ui

import com.mnlgt.elmetronomo.R

data class MetronomoUiState(
    var bpm: Int = 60,
    var andando: Boolean = false,
    val textoBoton: Int =
        if (andando) R.string.detener else R.string.iniciar
)