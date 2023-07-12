package com.mnlgt.elmetronomo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnlgt.elmetronomo.Metronomo
import com.mnlgt.elmetronomo.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MetronomoViewModel(
    private val metronomo: Metronomo
) : ViewModel() {

    private var _uiState = MutableStateFlow(MetronomoUiState())
    var uiState: StateFlow<MetronomoUiState> = _uiState.asStateFlow()
    //var texto = R.string.iniciar


    private var _metronomoJob: Job? = null


    private suspend fun iniciar() {
        //texto = R.string.detener
        _metronomoJob?.cancel()
        _uiState.value.andando = true
        _metronomoJob = viewModelScope.launch {
            metronomo.iniciar()
        }

    }

    private fun detener() {
        _metronomoJob?.cancel()
        _uiState.value.andando = false
    }

    suspend fun aprietaBoton() {
        if (_uiState.value.andando)
            detener()
        else
            iniciar()
    }

}