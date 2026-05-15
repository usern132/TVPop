package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TVShowListItemPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TVShowListItemPlaceholderPreview() {
    TVShowListItemPlaceholder(modifier = Modifier.fillMaxWidth())
}