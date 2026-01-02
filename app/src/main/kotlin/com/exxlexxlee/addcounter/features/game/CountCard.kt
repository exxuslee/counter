package com.exxlexxlee.addcounter.features.game

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.managers.rememberSoundManager
import com.exxlexxlee.addcounter.ui.common.HSpacer
import com.exxlexxlee.addcounter.ui.common.Icons
import com.exxlexxlee.addcounter.ui.common.RippleRadius
import com.exxlexxlee.addcounter.ui.theme.AppTheme


@Composable
fun CountCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    name: String,
    current: String = "0",
    background: Color = MaterialTheme.colorScheme.surfaceContainer,
    tint: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit,
    onDecrement: () -> Unit,
) {
    val soundManager = rememberSoundManager()
    var scale by remember { mutableFloatStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .scale(animatedScale)
            .pointerInput(onClick) {
                detectTapGestures(
                    onPress = {
                        scale = 0.95f
                        tryAwaitRelease()
                        scale = 1f
                    },
                    onTap = {
                        onClick()
                    }
                )
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            background,
                            background.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Иконка
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(Icons.icon(iconRes)),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = tint,
                            )
                        }
                    }

                    Text(
                        text = current,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = tint,
                    )

                    HSpacer(0.dp)

                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Box(
                            modifier = Modifier.zIndex(-1f)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = background.copy(alpha = 0.8f).compositeOver(tint),
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(
                                            radius = RippleRadius,
                                            bounded = false,
                                        )
                                    ) {
                                        onDecrement()
                                    },
                                border = BorderStroke(1.dp, tint.copy(alpha = 0.5f))
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.outline_undo_24),
                                        contentDescription = "Add photo",
                                        modifier = Modifier.size(20.dp),
                                        tint = tint,
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = tint,
                    )
                }

            }

        }
    }
}


@Preview
@Composable
fun CountCard_Preview() {
    AppTheme {
        CountCard(
            iconRes = R.drawable.outline_local_cafe_24, name = "Label", onClick = {}
        ) {

        }
    }
}