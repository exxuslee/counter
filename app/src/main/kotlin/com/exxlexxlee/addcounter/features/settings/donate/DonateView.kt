package com.exxlexxlee.addcounter.features.settings.donate

import android.content.ClipData
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.addcounter.R
import com.exxlexxlee.addcounter.features.settings.donate.models.DonateChainItem
import com.exxlexxlee.addcounter.features.settings.donate.models.DonateItem
import com.exxlexxlee.addcounter.features.settings.donate.models.Event
import com.exxlexxlee.addcounter.features.settings.donate.models.ViewState
import com.exxlexxlee.addcounter.ui.common.CellUniversalSection
import com.exxlexxlee.addcounter.ui.common.HsIconButton
import com.exxlexxlee.addcounter.ui.common.HsRow
import com.exxlexxlee.addcounter.ui.common.VFillSpacer
import com.exxlexxlee.addcounter.ui.common.VSpacer
import com.exxlexxlee.addcounter.ui.theme.AppTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val clipboard = LocalClipboard.current
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = stringResource(R.string.donate_header_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            DonationAmountSelector(
                selectedAmount = viewState.selectedAmount.toInt(),
                items = viewState.availableAmounts,
                onAmountSelected = { amount -> eventHandler(Event.OnAmountSelected(amount)) }
            )
            VSpacer(24.dp)
            CellUniversalSection(
                viewState.donates.map { donat ->
                    {
                        HsRow(
                            iconContent = {
                                Image(
                                    painterResource(donat.icon),
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = null,
                                )
                            },
                            titleContent = {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                        .weight(6f)
                                ) {
                                    Text(
                                        text = donat.chain,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Text(
                                        text = donat.address,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.MiddleEllipsis,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            },
                            onClick = {
                                eventHandler.invoke(Event.OnChainSelected(donat))
                            },
                            onSelect = donat == viewState.selectedChain,
                            arrowRight = false,
                        ) {
                            val scope = rememberCoroutineScope()
                            HsIconButton({
                                scope.launch {
                                    clipboard.setClipEntry(
                                        ClipEntry(
                                            ClipData.newPlainText(donat.chain, donat.address)
                                        )
                                    )
                                }

                                eventHandler.invoke(Event.AddressCopied)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_content_copy_24),
                                    contentDescription = stringResource(R.string.donate_copy_cd),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            )
            VSpacer(24.dp)

            DonationTickerSelector(
                items = viewState.tickers,
                selectedAmount = viewState.selectedTicker,
                onItemSelected = { item -> eventHandler(Event.OnTickerSelected(item)) }
            )
            VSpacer(12.dp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.donate_selected_prefix),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                        append(viewState.outAmount.padStart(6))
                    }
                    append(" ")
                    withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                        append(viewState.selectedTicker.label.padStart(4))
                    }
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
            )
        }

        VSpacer(12.dp)
        TextButton(
            onClick = {
                eventHandler.invoke(Event.OnDonate)
            },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                stringResource(R.string.donate).uppercase(),
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 36.dp)
            )
        }

        VFillSpacer(12.dp)
        if (viewState.isAddressCopied) Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.donate_footer_thanks),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        VFillSpacer(12.dp)
    }

}

@Composable
private fun DonationTickerSelector(
    items: List<DonateItem>,
    selectedAmount: DonateItem,
    onItemSelected: (DonateItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            DonateTickerButton(
                label = item.label,
                iconId = item.icon,
                isSelected = selectedAmount == item,
                onClick = { onItemSelected(item) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DonationAmountSelector(
    items: List<Pair<Int, Int>>,
    selectedAmount: Int,
    onAmountSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { amount ->
            DonateAmountButton(
                amount = amount.second,
                iconId = amount.first,
                isSelected = amount.second == selectedAmount,
                onClick = { onAmountSelected(amount.second) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DonateAmountButton(
    amount: Int,
    @DrawableRes iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = amount.toString()
                )
            }
        }
    }
}

@Composable
private fun DonateTickerButton(
    label: String,
    @DrawableRes iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = iconId),
                    modifier = Modifier.size(36.dp),
                    contentDescription = label
                )
            }
        }
    }
}

@Preview
@Composable
fun DonateView_Preview() {
    AppTheme {
        DonateView(
            viewState = ViewState(
                donates = DonateChainItem.chains(),
                tickers = DonateItem.btcList(),
                selectedTicker = DonateItem.Bitcoin,
                selectedChain = DonateChainItem.Bitcoin,
            ),
            eventHandler = {}
        )
    }
}
