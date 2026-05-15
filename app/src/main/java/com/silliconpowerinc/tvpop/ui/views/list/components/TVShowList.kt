package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun TVShowList(
    tvShowsFlow: Flow<PagingData<TVShow>>
) {
    val lazyPagingItems = tvShowsFlow.collectAsLazyPagingItems()
    LazyColumn {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { tvShow -> tvShow.id }
        ) { index ->
            val tvShow = lazyPagingItems[index]
            // item is loaded
            if (tvShow != null) {
                TVShowListItem(modifier = Modifier.fillMaxWidth(), tvShow = tvShow)
            }
            // item is still loading
            else {
                TVShowListItemPlaceholder()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TVShowListPreview() {
    TVShowList(flowOf(PagingData.from(TVShow.examples)))
}

@Composable
@Preview(showBackground = true, name = "Loading State")
private fun TVShowListLoadingPreview() {
    val loadingFlow = flowOf(PagingData.empty<TVShow>())
    TVShowList(tvShowsFlow = loadingFlow)
}