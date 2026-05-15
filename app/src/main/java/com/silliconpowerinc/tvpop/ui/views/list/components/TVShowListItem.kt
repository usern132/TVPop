package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.silliconpowerinc.tvpop.domain.models.TVShow

@Composable
fun TVShowListItem(
    tvShow: TVShow
) {
    Box {
        Text(text = tvShow.name)
    }
}

@Composable
@Preview(showBackground = true)
private fun TVShowListItemPreview() {
    TVShowListItem(TVShow.examples[0])
}