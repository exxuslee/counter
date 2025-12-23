package com.exxlexxlee.addcounter.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R


@Composable
fun ListEmptyView(
    paddingValues: PaddingValues = PaddingValues(),
    text: String,
    @DrawableRes icon: Int
) {
    ScreenMessageWithAction(
        paddingValues = paddingValues,
        text = text,
        icon = icon
    )
}

@Composable
fun ListLoadingView(
    text: String,
    paddingValues: PaddingValues = PaddingValues(),
) {
    ScreenMessageWithAction(
        paddingValues = paddingValues,
        text = text,
        icon = R.drawable.outline_sync_24
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun ScreenMessageWithAction(
    text: String,
    @DrawableRes icon: Int,
    paddingValues: PaddingValues = PaddingValues(),
    actionsComposable: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(icon),
                contentDescription = text,
                tint = MaterialTheme.colorScheme.outline
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(horizontal = 48.dp),
            text = text,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.secondary,
        )
        actionsComposable?.let { composable ->
            Spacer(Modifier.height(32.dp))
            composable.invoke()
        }
    }
}
