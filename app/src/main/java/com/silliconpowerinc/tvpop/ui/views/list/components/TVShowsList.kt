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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import com.silliconpowerinc.tvpop.ui.common.BORDER_PADDING_DP
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class TVShowListEvent

@Composable
fun TVShowsList(
    tvShowsFlow: Flow<PagingData<TVShow>>,
    onEvent: (event: TVShowListEvent) -> Unit
) {
    val lazyPagingItems = tvShowsFlow.collectAsLazyPagingItems()
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { lazyPagingItems.refresh() },
    ) {
        when (val state = lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> LoadingScreen()
            is LoadState.Error -> ErrorScreen(state, lazyPagingItems)
            else -> {
                LazyColumn {
                    items(
                        lazyPagingItems.itemCount,
                        key = lazyPagingItems.itemKey { tvShow -> tvShow.id }
                    ) { index ->
                        val tvShow = lazyPagingItems[index]
                        // item is loaded
                        if (tvShow != null)
                            TVShowsListItem(
                                modifier = Modifier.fillMaxWidth(),
                                tvShow = tvShow,
                                onEvent = onEvent
                            )
                        // item is still loading
                        else TVShowsListItemPlaceholder()
                    }
                }
            }
        }
    }

}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(BORDER_PADDING_DP.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun ErrorScreen(
    state: LoadState.Error,
    lazyPagingItems: LazyPagingItems<TVShow>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(BORDER_PADDING_DP.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
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
private fun TVShowsListPreview() {
    TVShowsList(
        tvShowsFlow = flowOf(PagingData.from(TVShow.examples)),
        onEvent = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun TVShowsListLoadingPreview() {
    val loadingFlow = flowOf(PagingData.empty<TVShow>())
    TVShowsList(
        tvShowsFlow = loadingFlow,
        onEvent = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun TVShowsListErrorPreview() {
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
    TVShowsList(
        tvShowsFlow = errorFlow,
        onEvent = {}
    )
}