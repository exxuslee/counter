package com.exxlexxlee.addcounter.ui.common

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun DiceRollDialog(
    onDismiss: () -> Unit
) {
    val diceImages = listOf(
        R.drawable.outline_dice_1_24,
        R.drawable.outline_dice_2_24,
        R.drawable.outline_dice_3_24,
        R.drawable.outline_dice_4_24,
        R.drawable.outline_dice_5_24,
        R.drawable.outline_dice_6_24
    )

    var currentDice by remember { mutableIntStateOf(0) }
    var rolling by remember { mutableStateOf(false) }

    LaunchedEffect(rolling) {
        if (rolling) {
            while (rolling) {
                currentDice = Random.nextInt(diceImages.size)
                delay(Random.nextLong(200, 400))
            }
            currentDice = Random.nextInt(diceImages.size)
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (rolling) 560f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        finishedListener = { rolling = false }
    )

    val shakeOffsetX by animateFloatAsState(
        targetValue = if (rolling) (Random.nextFloat() * 140 - 80) else 0f,
        animationSpec = if (rolling) {
            infiniteRepeatable(
                animation = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        } else {
            tween(300)
        }
    )

    val shakeOffsetY by animateFloatAsState(
        targetValue = if (rolling) (Random.nextFloat() * 140 - 80) else 0f,
        animationSpec = if (rolling) {
            infiniteRepeatable(
                animation = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        } else {
            tween(300)
        }
    )

    val view = LocalView.current
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    if (!rolling) rolling = true
                }
            ) {
                Text(stringResource(R.string.throw_dice))
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.outline_dice_5_24),
                    contentDescription = stringResource(id = R.string.dice),
                )
                HSpacer(8.dp)
                Text(text = stringResource(id = R.string.dice))
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            if (!rolling) rolling = true
                        },
                    )
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = diceImages[currentDice]),
                    contentDescription = stringResource(R.string.dice),
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                            translationX = shakeOffsetX
                            translationY = shakeOffsetY
                        }
                )
            }
        }
    )
}