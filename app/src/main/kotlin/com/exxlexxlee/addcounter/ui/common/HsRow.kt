package com.exxlexxlee.addcounter.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R


@Composable
fun HsRow(
    imageVector: ImageVector,
    titleContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    onSelect: Boolean = false,
    arrowRight: Boolean = false,
    valueContent: (@Composable () -> Unit)? = null,
) {
    RowUniversal(
        modifier = Modifier
            .background(
                if (onSelect) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            )
            .padding(horizontal = 12.dp), onClick = onClick
    ) {
        Icon(
            imageVector,
            modifier = Modifier.size(30.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
        )
        titleContent()
        Spacer(Modifier.weight(1f))
        if (valueContent != null) valueContent()

        if (arrowRight) Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
fun HsRow(
    iconContent: @Composable () -> Unit,
    titleContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    onSelect: Boolean = false,
    arrowRight: Boolean = false,
    valueContent: (@Composable () -> Unit)? = null,
) {
    RowUniversal(
        modifier = Modifier
            .background(
                if (onSelect) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            )
            .padding(horizontal = 12.dp), onClick = onClick
    ) {
        iconContent()
        titleContent()
        Spacer(Modifier.weight(1f))
        if (valueContent != null) valueContent()

        if (arrowRight) Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}