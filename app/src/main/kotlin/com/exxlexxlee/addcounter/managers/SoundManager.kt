package com.exxlexxlee.addcounter.managers

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.exxlexxlee.addcounter.managers.ClickSound.SUB_BONUS
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_0
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_1
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_2
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_3
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_4
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_5
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_6
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_7
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_8
import com.exxlexxlee.addcounter.managers.ClickSound.TONE_9
import kotlin.also
import kotlin.runCatching

class SoundManager(context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    fun play(sound: ClickSound) {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) <= 0) return
        runCatching {
            toneGenerator.startTone(sound.tone, sound.durationMs)
        }
    }

    fun play(number: Int) {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) <= 0) return
        runCatching {
            val sound = when (number) {
                0 -> TONE_0
                1 -> TONE_1
                2 -> TONE_2
                3 -> TONE_3
                4 -> TONE_4
                5 -> TONE_5
                6 -> TONE_6
                7 -> TONE_7
                8 -> TONE_8
                9 -> TONE_9
                else -> SUB_BONUS
            }
            toneGenerator.startTone(sound.tone, sound.durationMs)
        }
    }

    fun release() {
        runCatching { toneGenerator.release() }
    }
}

enum class ClickSound(
    val tone: Int,
    val durationMs: Int = 100
) {
    TONE_0(ToneGenerator.TONE_DTMF_0),
    TONE_1(ToneGenerator.TONE_DTMF_1),
    TONE_2(ToneGenerator.TONE_DTMF_2),
    TONE_3(ToneGenerator.TONE_DTMF_3),
    TONE_4(ToneGenerator.TONE_DTMF_4),
    TONE_5(ToneGenerator.TONE_DTMF_5),
    TONE_6(ToneGenerator.TONE_DTMF_6),
    TONE_7(ToneGenerator.TONE_DTMF_7),
    TONE_8(ToneGenerator.TONE_DTMF_8),
    TONE_9(ToneGenerator.TONE_DTMF_9),
    SUB_BONUS(ToneGenerator.TONE_SUP_BUSY);

}

@Composable
fun rememberSoundManager(): SoundManager {
    val context = LocalContext.current
    return remember {
        SoundManager(context)
    }.also { manager ->
        DisposableEffect(Unit) {
            onDispose {
                manager.release()
            }
        }
    }
}