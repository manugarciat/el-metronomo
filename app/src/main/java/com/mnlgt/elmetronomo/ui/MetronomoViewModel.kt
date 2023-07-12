package com.mnlgt.elmetronomo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnlgt.elmetronomo.Metronomo
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MetronomoViewModel(
    private val metronomo: Metronomo
) : ViewModel() {

    private val _uiState = MutableStateFlow(MetronomoUiState())
    val uiState: StateFlow<MetronomoUiState> = _uiState.asStateFlow()

    private var _metronomoJob: Job? = null

    private suspend fun iniciar() {

        //_metronomoJob?.cancel()
        _uiState.update { currentState ->
            currentState.copy(
                andando = true
            )
        }
        _metronomoJob = viewModelScope.launch {
            metronomo.iniciar()
            //while (true) {} leo variable de metronomo y actualizo estado
        }
    }

    private fun detener() {
        _metronomoJob?.cancel()
        _uiState.update { currentState ->
            currentState.copy(
                andando = false
            )
        }
    }

    suspend fun aprietaBoton() {
        if (_uiState.value.andando)
            detener()
        else
            iniciar()
    }

}
