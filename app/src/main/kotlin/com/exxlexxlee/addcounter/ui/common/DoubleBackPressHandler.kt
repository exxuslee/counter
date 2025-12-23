package com.exxlexxlee.addcounter.ui.common

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.exxlexxlee.addcounter.R


class DoubleBackPressHandler(
    private val activity: ComponentActivity,
    private val doublePressDelay: Long = 2000L
) {
    private var backPressedTime = 0L
    private var toast: Toast? = null

    fun handleBackPress(): Boolean {
        val currentTime = System.currentTimeMillis()
        
        return if (currentTime - backPressedTime > doublePressDelay) {
            backPressedTime = currentTime
            showToast()
            true
        } else {
            activity.finish()
            false
        }
    }

    private fun showToast() {
        toast?.cancel()
        toast = Toast.makeText(activity, activity.getString(R.string.double_back_press_message), Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun cleanup() {
        toast?.cancel()
    }
}

@Composable
fun rememberDoubleBackPressHandler(
    activity: ComponentActivity,
    doublePressDelay: Long = 2000L
): DoubleBackPressHandler {
    val handler = remember { 
        DoubleBackPressHandler(activity, doublePressDelay) 
    }
    
    DisposableEffect(Unit) {
        onDispose {
            handler.cleanup()
        }
    }
    
    return handler
}
