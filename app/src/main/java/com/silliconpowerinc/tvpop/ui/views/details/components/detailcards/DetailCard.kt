package com.silliconpowerinc.tvpop.ui.views.details.components.detailcards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.ui.theme.AppTypography

/**
 * A generic card used to display a specific detail with a title.
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param title The title of the detail.
 * @param content The composable content of the detail.
 */
@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = AppTypography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            content()
        }
    }
}

