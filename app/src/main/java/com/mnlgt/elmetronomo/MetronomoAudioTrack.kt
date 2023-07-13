package com.mnlgt.elmetronomo

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sin

class MetronomoAudioTrack {

    private val sampleRate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC)
    private val bufferSizeBytes = AudioTrack.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    private var numSamples: Int
    private var samples: DoubleArray
    private var generatedSnd: ShortArray

    private var audioTrack: AudioTrack

    private var tempo: Int = 60

    init {

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

        numSamples = audioTrack.bufferSizeInFrames
        samples = DoubleArray(numSamples)
        generatedSnd = ShortArray(numSamples)

//        for (i in 0 until numSamples) {
//            val freqOfTone = 440.0
//            samples[i] =
//                sin(2 * Math.PI * i / (sampleRate / freqOfTone))
//        }
//
//        var idx = 0
//        for (dVal in samples) {
//            val `val` = (dVal * 32767).toInt().toShort()
//            generatedSnd[idx++] = `val`.and(0x00ff).toByte()
//
//            //var temp = `val`.and((0xff00 ushr 8).toShort()).toByte()
//            generatedSnd[idx++] = `val`.and((0xff00 ushr 8).toShort()).toByte()
//
//
//        }

    }


    //genera sample para reproducir, el largo es en cantidad de samples por ahora
    private fun generateSample(largo: Int): DoubleArray {

        val result = DoubleArray(largo)
        for (i in 0 until largo) {
            result[i] = sin(2 * Math.PI * i / (sampleRate / 330.0))
        }
        return result
    }

    //convierte el sample en byte arrays para el formato 16 bit pcm que acepta audiotrack
    private fun to16BitPCM(input: DoubleArray): ShortArray {

        val result = ShortArray(input.size)
        //var index = 0
        for (i in input.indices) {

            //escalar a la maxima amplitud
            val valShort: Short = (input[i] * Short.MAX_VALUE).toInt().toShort()
            result[i] = valShort
//            result[index++] = valShort.and(0x00ff).toByte()

            //var temp = valShort.and(0xff00.toShort())
//            result[index++] = valShort.and((0xff00 ushr 8).toShort()).toByte()
        }
        return result
    }

    private fun calcularSilencio(largoSample: Int): Int {
        return (((60.0 / tempo.toFloat()) * sampleRate)).toInt() - largoSample
    }


    suspend fun iniciar() {

        val sonido = generateSample(sampleRate / 8)
        //val sonidoPCM = to16BitPCM(sonido)
        //val silencio = calcularSilencio(sonido.size)

        var t = 0
        var s = 0

        audioTrack.play()

        while (true) {
            val silencio = calcularSilencio(sonido.size)
            withContext(Dispatchers.Default) {

                for (i in generatedSnd.indices) {

                    if (t < sonido.size) {
                        if (t < sonido.size - 1000) {
                            generatedSnd[i] = (sonido[t] * Short.MAX_VALUE).toInt().toShort()
                        } else {
                            generatedSnd[i] =
                                ((sonido[t] * Short.MAX_VALUE) * ((sonido.size - t) / 1000.0)).toInt()
                                    .toShort()
                        }
                        t++
                    } else {
                        generatedSnd[i] = 0
                        s++
                        if (s >= silencio) {
                            t = 0
                            s = 0
                        }
                    }
                }
                //generatedSnd = to16BitPCM(samples)
                audioTrack.write(generatedSnd, 0, generatedSnd.size)
            }

        }
    }

    fun setTempo(t: Int) {
        tempo = t
    }

}

