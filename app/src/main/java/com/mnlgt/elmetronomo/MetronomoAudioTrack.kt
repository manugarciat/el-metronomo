package com.mnlgt.elmetronomo

import android.media.AudioTrack
import com.mnlgt.elmetronomo.data.acentoInicial
import com.mnlgt.elmetronomo.data.subDivInicial
import com.mnlgt.elmetronomo.data.tempoInicial
import com.mnlgt.elmetronomo.data.volumenFactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.experimental.and
import kotlin.math.sin

class MetronomoAudioTrack {

    //audiotrack
    private var _audioTrack: AudioTrack = obtenerAudioTrack()

    //cantidad de samples que vamos a imprimir en cada bloque, igual al buffer size
    private var _numSamples: Int = _audioTrack.bufferSizeInFrames

    //array donde guardamos los samples calculados para escribir en audiotrack, valor entero de 16 bit (short, 2 bytes)
    private var _generatedSnd: ShortArray = ShortArray(_numSamples)

    //variables para la escritura del audio, lleva la cantidad de samples impresos del tick y del silencio
    private var sndPosition = 0
    private var silPosition = 0
    private var beatPosition = 1

    //variables de metronomo
    private var _tempo: Float = tempoInicial
    private var _subdivision: Int = subDivInicial
    private var _acentoRate: Int = acentoInicial

    //variables para detener:
    private var beginStop: Boolean = false
    private var fadeLength: Int = 4000
    private var fadePosition: Int = 0
    private var detenerAhora: Boolean = false


    //genera sample para reproducir, el largo es en cantidad de samples, devuelve array de numeros entre -1 y 1 (Double)
    private fun generateSample(largo: Int, frecuencia: Double, volumen: Double = 1.0): DoubleArray {

        val fadeIn = 100
        val fadeOut = 2000

        val result = DoubleArray(largo)
        for (i in 0 until largo) {

            when (i) {
                in 0 until fadeIn -> { //fade in
                    result[i] =
                        sin(2 * Math.PI * i / (_audioTrack.sampleRate / frecuencia)) * (i / fadeIn.toDouble()) * volumenFactor * volumen
                }

                in fadeIn until largo - fadeOut -> {
                    result[i] =
                        sin(2 * Math.PI * i / (_audioTrack.sampleRate / frecuencia)) * volumenFactor * volumen
                }

                in largo - fadeOut until largo -> { //fade out
                    result[i] =
                        sin(2 * Math.PI * i / (_audioTrack.sampleRate / frecuencia)) * ((largo - i) / fadeOut.toDouble()) * volumenFactor * volumen
                }
            }
        }
        return result
    }

    private fun to16Bit(input: DoubleArray): ShortArray {
        val result = ShortArray(input.size)
        for (i in input.indices) {
            result[i] = (input[i] * Short.MAX_VALUE).toInt().toShort()
        }
        return result
    }

    //convierte el sample en byte arrays para el formato 16 bit pcm (en bytes, 2 bytes por sample) que acepta audiotrack (no se si funciona bien)
    private fun to16BitPCM(input: DoubleArray): ByteArray {

        val result = ByteArray(input.size * 2)
        var index = 0
        for (i in input.indices) {

            //escalar a la maxima amplitud
            val valShort: Short = (input[i] * Short.MAX_VALUE).toInt().toShort()
            result[index++] = valShort.and(0x00ff).toByte()
            result[index++] = valShort.and((0xff00 ushr 8).toShort()).toByte()
        }
        return result
    }

    private fun calcularSilencio(duracioSonido: Int, duracionSubdivision: Int): Int {
        val duracionBeat = ((60.0 / _tempo) * _audioTrack.sampleRate).toInt()
        return ((duracionBeat - (duracioSonido* _subdivision)) / _subdivision) //- (duracionSubdivision * (_subdivision - 1))
    }

    suspend fun iniciar() {

        sndPosition = 0
        silPosition = 0
        beatPosition = 1

        fadePosition = 0
        beginStop = false
        detenerAhora = false

        val tick16bit = to16Bit(generateSample(_audioTrack.sampleRate / 10, 360.0))
        val acento16bit = to16Bit(generateSample(_audioTrack.sampleRate / 10, 432.0))
        val subSonido16Bit = to16Bit(generateSample(_audioTrack.sampleRate / 10, 360.0, 0.2))

        _audioTrack.play()

        withContext(Dispatchers.Default) {
            while (!detenerAhora) {

                imprimirAudio(tick16bit, acento16bit, subSonido16Bit)
                _audioTrack.write(_generatedSnd, 0, _generatedSnd.size)
            }

            _audioTrack.stop()
            //_audioTrack.release()
        }
    }

    private fun imprimirAudio(tick: ShortArray, acento: ShortArray, subS: ShortArray) {

        val silencio = calcularSilencio(tick.size, subS.size)

        for (i in _generatedSnd.indices) {

            val sonido = if (beatPosition % _subdivision != 1 && _subdivision != 1) subS else
                if (beatPosition == 1) acento else tick

            if (sndPosition < sonido.size) {
                _generatedSnd[i] = sonido[sndPosition]
                sndPosition++
            } else {
                _generatedSnd[i] = 0
                silPosition++

                if (silPosition >= silencio) {
                    sndPosition = 0
                    silPosition = 0
                    if (beatPosition < _acentoRate * _subdivision) beatPosition++
                    else beatPosition = 1
                }
            }

            if (beginStop) {
                if (fadePosition >= fadeLength) {
                    detenerAhora = true
                    _generatedSnd[i] = 0
                } else {
                    //genera fade out:
                    _generatedSnd[i] =
                        (_generatedSnd[i] * ((fadeLength - fadePosition) / fadeLength.toFloat())).toInt()
                            .toShort()
                    fadePosition++
                }
            }
        }
    }

    fun setTempo(t: Float) {
        _tempo = t
    }

    fun detener() {
        beginStop = true
    }

    fun setAcento(acento: Int) {
        _acentoRate = acento
    }

    fun setSubdivision(sub: Int) {
        _subdivision = sub
    }

}

