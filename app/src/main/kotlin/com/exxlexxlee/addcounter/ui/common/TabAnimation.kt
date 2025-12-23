package com.exxlexxlee.addcounter.ui.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

class TabAnimation {

    companion object {
        private const val TAB_ANIMATION_DURATION = 400

        fun enterTransition(
            initialTab: Int,
            targetTab: Int
        ): EnterTransition {
            return if (targetTab > initialTab) {
                slideInHorizontally(
                    initialOffsetX = { it }
                ) + fadeIn()
            } else {
                slideInHorizontally(
                    initialOffsetX = { -it }
                ) + fadeIn()
            }
        }

        fun exitTransition(
            initialTab: Int,
            targetTab: Int
        ): ExitTransition {
            return if (targetTab > initialTab) {
                slideOutHorizontally(
                    targetOffsetX = { -it }
                ) + fadeOut()
            } else {
                slideOutHorizontally(
                    targetOffsetX = { it }
                ) + fadeOut()
            }
        }
    }
}