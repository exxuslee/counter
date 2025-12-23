package com.exxlexxlee.addcounter.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun HsIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    rippleColor: Color = MaterialTheme.colorScheme.outlineVariant,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .defaultMinSize(48.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = ripple(
                    radius = RippleRadius,
                    bounded = false,
                    color = rippleColor
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor =
            if (enabled) LocalContentColor.current
            else LocalContentColor.current.copy(alpha = DisabledAlpha)

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = content
        )
    }
}

private val RippleRadius = 24.dp
private const val DisabledAlpha = 0.38f