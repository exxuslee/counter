package com.exxlexxlee.addcounter.features.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.ui.common.HsRow
import com.exxlexxlee.addcounter.ui.common.Icons

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int?,
    name: String = stringResource(R.string.name_icon),
    level: String = stringResource(R.string.level),
    bonus: String = stringResource(R.string.bonus),
    life: String = stringResource(R.string.life),
    selected: Boolean = false,
    onSelectIcon: () -> Unit = {},
    onSelectRow: (() -> Unit)? = null,
) {
    Row(modifier) {
        val icon = painterResource(iconRes?.let { Icons.icon(it) } ?: R.drawable.sex)
        HsRow(
            onSelect = selected,
            onClick = onSelectRow,
            iconContent = {
                Text(
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                    text = level,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Image(
                    icon,
                    modifier = Modifier
                        .clickable(
                            enabled = iconRes != null,
                            onClick = onSelectIcon
                        )
                        .size(36.dp),

                    contentDescription = stringResource(R.string.icon),
                )
            },
            titleContent = {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = name,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary,
                )
            },
            valueContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                        text = bonus,
                        fontSize = iconRes?.let { 26.sp } ?: 28.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        modifier = Modifier.defaultMinSize(minWidth = 48.dp),
                        text = life,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

            },
            arrowRight = false,
        )
    }

}