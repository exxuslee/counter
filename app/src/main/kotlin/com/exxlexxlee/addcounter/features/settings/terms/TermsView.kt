package com.exxlexxlee.addcounter.features.settings.terms

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.settings.terms.models.Event
import com.exxlexxlee.addcounter.features.settings.terms.models.ViewState
import com.exxlexxlee.addcounter.ui.common.HeaderStick
import com.exxlexxlee.addcounter.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val content = remember {
        listOf(
            R.string.terms_section_1_title to R.string.terms_section_1_content,
            R.string.terms_section_2_title to R.string.terms_section_2_content,
            R.string.terms_section_3_title to R.string.terms_section_3_content,
            R.string.terms_section_4_title to R.string.terms_section_4_content,
            R.string.terms_section_5_title to R.string.terms_section_5_content,
            R.string.terms_section_6_title to R.string.terms_section_6_content,
            R.string.terms_section_7_title to R.string.terms_section_7_content,
            R.string.terms_section_8_title to R.string.terms_section_8_content,
            R.string.terms_section_9_title to R.string.terms_section_9_content,
            R.string.terms_section_10_title to R.string.terms_section_10_content,
            R.string.terms_section_11_title to R.string.terms_section_11_content,
        )
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(
                    R.string.terms_last_updated,
                    stringResource(R.string.terms_default_date)
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        content.forEach { section ->
            stickyHeader {
                HeaderStick(stringResource(section.first))
            }

            item {
                Text(
                    text = stringResource(section.second),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.terms_footer),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }


        item {
            val view = LocalView.current
            Button(
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    eventHandler(Event.ReadTerms)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp,24.dp,24.dp,64.dp),
                enabled = !viewState.isTermsOfUseRead
            ) {
                Text(
                    text = stringResource(R.string.terms_accept_button).uppercase(),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun TermsView_Preview() {
    AppTheme {
        TermsView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}