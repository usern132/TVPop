package com.silliconpowerinc.tvpop.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.silliconpowerinc.tvpop.ui.navigation.MainNavHost
import com.silliconpowerinc.tvpop.ui.theme.AppTypography

/**
 * The main scaffold of the app, which contains the top app bar and the navigation host.
 */
@Composable
fun MainScaffold() {
    MainScaffoldContent { paddingValues ->
        MainNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = rememberNavController()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffoldContent(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TVPop",
                        style = AppTypography.headlineMedium,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScaffoldPreview() {
    MainScaffoldContent {}
}
