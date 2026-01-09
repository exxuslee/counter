package com.exxlexxlee.addcounter.features.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.BuildConfig
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.ui.common.HeaderStick
import com.exxlexxlee.addcounter.ui.common.VSpacer
import com.exxlexxlee.addcounter.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutView() {
    Column {
        val sections = listOf(
            stringResource(R.string.application_title) to stringResource(R.string.application_description),
            stringResource(R.string.how_it_works_title) to stringResource(R.string.how_it_works_body),
            stringResource(R.string.version_title) to BuildConfig.VERSION_NAME,
            stringResource(R.string.privacy_title) to stringResource(R.string.privacy_text)
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            sections.forEach { (header, body) ->
                stickyHeader {
                    HeaderStick(header)
                }
                item {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            stickyHeader {
                HeaderStick("Social links")
            }
            item {
                val uriHandler = LocalUriHandler.current
                FlowRow(
                    modifier = Modifier.padding(bottom = 24.dp),
                    maxItemsInEachRow = Int.MAX_VALUE, // автоматически переносит
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // отступы по горизонтали
                    verticalArrangement = Arrangement.spacedBy(8.dp) // отступы по вертикали
                ) {
                    Button(onClick = { uriHandler.openUri("https://x.com/") }) {
                        Text("X")
                    }
                    Button(onClick = { uriHandler.openUri("https://meta.com/") }) {
                        Text("Meta")
                    }
                    Button(onClick = { uriHandler.openUri("https://t.me/") }) {
                        Text("Telegram")
                    }
                }
            }

            stickyHeader {
                HeaderStick(stringResource(R.string.rate_app_title))
            }
            item {
                val uriHandler = LocalUriHandler.current
                val playStoreUrl = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { uriHandler.openUri(playStoreUrl) }) {
                        Text(stringResource(R.string.rate_app_button))
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = "2025 Counter 123 ©",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    VSpacer(48.dp)
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutView_Preview() {
    AppTheme {
        AboutView()
    }
}
