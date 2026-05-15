package com.silliconpowerinc.tvpop.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.silliconpowerinc.tvpop.ui.navigation.MainNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TVPop") }
            )
        }
    ) { paddingValues ->
        MainNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    MainScaffold()
}
