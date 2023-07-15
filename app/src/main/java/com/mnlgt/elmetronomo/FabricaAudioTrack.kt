package com.mnlgt.elmetronomo

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack


fun obtenerAudioTrack(): AudioTrack {

    //variables de audio:
    val sampleRate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC)
    val bufferSizeBytes = AudioTrack.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    ) * 3 //multiplico por 4 para mejorar rendimiento cuando cambia configuracion por ejemplo

    //audiotrack
    val audioTrack: AudioTrack

    //prepara audio attributes
    val attribBuilder = AudioAttributes.Builder()
    attribBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
    attribBuilder.setUsage(AudioAttributes.USAGE_MEDIA)

    val attributes = attribBuilder.build()

    // Build audio format
    val afBuilder = AudioFormat.Builder()
    afBuilder.setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
    afBuilder.setEncoding(AudioFormat.ENCODING_PCM_16BIT)
    afBuilder.setSampleRate(sampleRate)

    val format = afBuilder.build()

    audioTrack = AudioTrack(
        attributes,
        format,
        bufferSizeBytes,
        AudioTrack.MODE_STREAM,
        AudioManager.AUDIO_SESSION_ID_GENERATE
    )

    return audioTrack
}

