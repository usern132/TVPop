package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.silliconpowerinc.tvpop.R
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
            if (tvShow != null)
                TVShowListItem(modifier = Modifier.fillMaxWidth(), tvShow = tvShow)
            // item is still loading
            else TVShowListItemPlaceholder()
        }
    }

    // Overlay shown with states other than loaded
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> CircularProgressIndicator(modifier = Modifier.size(32.dp))
            is LoadState.Error -> ErrorScreen(state, lazyPagingItems)
            else -> {}
        }
    }

}

@Composable
private fun ErrorScreen(
    state: LoadState.Error,
    lazyPagingItems: LazyPagingItems<TVShow>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(
                R.string.error_occurred,
                state.error.localizedMessage
            )
        )
        Button(
            onClick = { lazyPagingItems.retry() },
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TVShowListPreview() {
    TVShowList(flowOf(PagingData.from(TVShow.examples)))
}

@Composable
@Preview(showBackground = true)
private fun TVShowListLoadingPreview() {
    val loadingFlow = flowOf(PagingData.empty<TVShow>())
    TVShowList(tvShowsFlow = loadingFlow)
}

@Composable
@Preview(showBackground = true)
private fun TVShowListErrorPreview() {
    val errorFlow = flowOf(
        PagingData.from(
            data = emptyList<TVShow>(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Error(Exception("Network error")),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false)
            )
        )
    )
    TVShowList(tvShowsFlow = errorFlow)
}