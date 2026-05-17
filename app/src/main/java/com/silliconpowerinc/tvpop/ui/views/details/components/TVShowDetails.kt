package com.silliconpowerinc.tvpop.ui.views.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.silliconpowerinc.tvpop.domain.models.TVShow

@Composable
fun TVShowDetails(
    tvShow: TVShow
) {
    Column {
        Text(tvShow.id.toString())
        Text(tvShow.originCountry[0])
        Text(tvShow.overview)
    }
}

@Composable
@Preview(showBackground = true)
private fun TVShowDetailsPreview() {
    TVShowDetails(TVShow.examples[0])
}