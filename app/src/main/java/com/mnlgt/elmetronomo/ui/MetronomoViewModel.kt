package com.mnlgt.elmetronomo.ui

import android.content.Context
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnlgt.elmetronomo.Metronomo
import com.mnlgt.elmetronomo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetronomoViewModel(
    private val metronomo: Metronomo
) : ViewModel() {

    private val _uiState = MutableStateFlow(MetronomoUiState())
    val uiState: StateFlow<MetronomoUiState> = _uiState.asStateFlow()


    private var _metronomoJob: Job? = null


    suspend fun iniciar() {
        viewModelScope.launch {
            metronomo.iniciar()
        }

    }

}