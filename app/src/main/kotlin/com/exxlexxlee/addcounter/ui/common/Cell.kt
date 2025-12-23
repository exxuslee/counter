package com.exxlexxlee.addcounter.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CellLazyMultilineSection(
    composableItems: List<@Composable () -> Unit>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        composableItems.forEachIndexed { index, composable ->
            CellMultilineLawrence(borderTop = index != 0) {
                composable()
            }
        }
    }
}

@Composable
fun <T> CellLazyMultilineSection(
    items: Iterable<T>,
    onLoadMore: () -> Unit,
    isLoading: Boolean = false,
    hasMoreItems: Boolean = true,
    loadMoreThreshold: Int = 3,
    itemContent: @Composable (T) -> Unit
) {
    val itemsList = items.toList()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        itemsIndexed(itemsList) { index, data ->
            CellMultilineLawrence(borderTop = index != 0) {
                itemContent(data)
            }

            if (index >= itemsList.size - loadMoreThreshold &&
                hasMoreItems &&
                !isLoading
            ) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }

        if (isLoading) item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
fun CellMultilineLawrence(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (borderTop) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        content.invoke()
    }
}

@Composable
fun <T> CellSingleLineLawrenceSection(
    items: List<T>,
    itemContent: @Composable (T) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        items.forEachIndexed { index, marketDataLine ->
            CellSingleLineLawrence(borderTop = index != 0) {
                itemContent(marketDataLine)
            }
        }
    }

}

@Composable
fun CellSingleLineLawrenceSection(
    content: @Composable () -> Unit
) {
    CellSingleLineLawrenceSection(listOf(content))
}

@Composable
fun CellSingleLineLawrenceSection(
    composableItems: List<@Composable () -> Unit>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        composableItems.forEachIndexed { index, composable ->
            CellSingleLineLawrence(borderTop = index != 0) {
                composable()
            }
        }
    }

}

@Composable
fun HSSectionRounded(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        content()
    }
}

@Composable
fun Item(content: @Composable () -> Unit) {
    CellSingleLineLawrence(
        content = content
    )
}

@Composable
fun CellSingleLineLawrence(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    content: @Composable () -> Unit,
) {
    CellSingleLine(
        borderTop = borderTop,
        borderBottom = borderBottom,
        color = MaterialTheme.colorScheme.surfaceVariant,
        content = content
    )
}

@Composable
fun CellSingleLine(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    color: Color? = null,
    content: @Composable () -> Unit,
) {
    val colorModifier = when {
        color != null -> Modifier.background(color)
        else -> Modifier
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .then(colorModifier)
    ) {
        if (borderTop) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        content.invoke()
    }
}

@Composable
fun CellMultilineClear(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    height: Dp = 56.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val clickableModifier = when (onClick) {
        null -> Modifier
        else -> Modifier.clickable {
            onClick.invoke()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .then(clickableModifier)
    ) {
        if (borderTop) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        content.invoke()
    }
}

@Composable
fun CellSingleLineClear(
    modifier: Modifier = Modifier,
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        if (borderTop) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun RowUniversal(
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 8.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val clickableModifier = when (onClick) {
        null -> Modifier
        else -> Modifier.clickable {
            onClick.invoke()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 28.dp)
            .then(clickableModifier)
            .then(modifier)
            .padding(vertical = verticalPadding),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        content = content
    )
}

@Composable
fun CellUniversalSection(
    content: @Composable () -> Unit
) {
    CellUniversalSection(listOf(content))
}

@Composable
fun CellUniversalSection(
    composableItems: List<@Composable () -> Unit>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        composableItems.forEachIndexed { index, composable ->
            SectionUniversalItem(
                borderTop = index != 0,
            ) {
                composable()
            }
        }
    }
}

@Composable
fun <T> CellUniversalSection(
    items: Iterable<T>,
    showFrame: Boolean = false,
    itemContent: @Composable (T) -> Unit
) {
    val frameModifier = if (showFrame) {
        Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
    } else {
        Modifier
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(frameModifier)
    ) {
        items.forEachIndexed { index, itemData ->
            SectionUniversalItem(
                borderTop = index != 0,
            ) {
                itemContent(itemData)
            }
        }
    }
}

@Composable
fun CellUniversalSection(
    showFrame: Boolean = false,
    content: @Composable () -> Unit
) {
    val frameModifier = if (showFrame) {
        Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
    } else {
        Modifier
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(frameModifier)
    ) {
        SectionUniversalItem {
            content()
        }
    }
}

@Composable
fun SectionUniversalItem(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    content: @Composable () -> Unit,
) {

    //content items should use RowUniversal

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (borderTop) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        content.invoke()
    }

}

@Composable
fun SectionItemBorderedRowUniversalClear(
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Column {
        if (borderTop) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 12.dp),
            onClick = onClick,
            content = content
        )
        if (borderBottom) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
fun CellBorderedRowUniversal(
    modifier: Modifier = Modifier,
    borderTop: Boolean = false,
    borderBottom: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        if (borderTop) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        if (borderBottom) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

        RowUniversal(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            content = content
        )
    }
}

@Composable
fun <T> CellUniversalLawrenceSection(
    items: Iterable<T>,
    showFrame: Boolean = false,
    itemContent: @Composable (T) -> Unit
) {
    val frameModifier = if (showFrame) {
        Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
    } else {
        Modifier
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .then(frameModifier)
    ) {
        items.forEachIndexed { index, itemData ->
            SectionUniversalItem(
                borderTop = index != 0,
            ) {
                itemContent(itemData)
            }
        }
    }
}
