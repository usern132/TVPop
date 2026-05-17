package com.silliconpowerinc.tvpop.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.silliconpowerinc.tvpop.ui.views.details.DetailsScreen
import com.silliconpowerinc.tvpop.ui.views.list.ListScreen
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowListItemEvent
import kotlinx.serialization.Serializable

sealed interface MainRoute {
    @Serializable
    object List : MainRoute

    @Serializable
    data class Details(val id: Int) : MainRoute
}

private const val ANIM_DURATION = 250

val enterTransition =
    slideInHorizontally(animationSpec = tween(ANIM_DURATION), initialOffsetX = { it })
val exitTransition =
    slideOutHorizontally(animationSpec = tween(ANIM_DURATION), targetOffsetX = { -it })
val popEnterTransition =
    slideInHorizontally(animationSpec = tween(ANIM_DURATION), initialOffsetX = { -it })
val popExitTransition =
    slideOutHorizontally(animationSpec = tween(ANIM_DURATION), targetOffsetX = { it })

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute.List,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition }
    ) {
        composable<MainRoute.List> {
            ListScreen(onNavigate = { event ->
                when (event) {
                    is TVShowListItemEvent.Click -> navController.navigate(MainRoute.Details(event.id))
                }
            })
        }
        composable<MainRoute.Details> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.Details>()
            DetailsScreen(tvShowId = route.id)
        }
    }
}