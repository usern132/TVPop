package com.silliconpowerinc.tvpop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.silliconpowerinc.tvpop.ui.views.list.ListScreen
import kotlinx.serialization.Serializable

sealed interface MainRoute {
    @Serializable
    object List : MainRoute
    @Serializable
    object Details : MainRoute
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute.List,
    ) {
        composable<MainRoute.List> {
            ListScreen()
        }
    }
}