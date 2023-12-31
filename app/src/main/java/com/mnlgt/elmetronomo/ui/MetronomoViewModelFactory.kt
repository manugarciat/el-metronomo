package com.mnlgt.elmetronomo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnlgt.elmetronomo.MetronomoAudioTrack

class MetronomoViewModelFactory(private val metronomo: MetronomoAudioTrack) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetronomoViewModel::class.java)) {
            return MetronomoViewModel(metronomo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
