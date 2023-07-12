package com.mnlgt.elmetronomo

import android.media.SoundPool

class AudioManager(context: MainActivity) {
    private val _soundP: SoundPool = SoundPool.Builder().setMaxStreams(1).build()
    private val rim = _soundP.load(context, R.raw.rim, 1)
    private val tick = _soundP.load(context, R.raw.tick, 1)

    fun playTick() {
        _soundP.play(rim,1F, 1F, 1, 0, 1F)
    }
}
