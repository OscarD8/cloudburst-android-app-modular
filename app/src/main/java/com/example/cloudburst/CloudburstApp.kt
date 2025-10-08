package com.example.cloudburst

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Screen
import com.example.ui.common.CloudburstBackground
import com.example.ui.common.CloudburstNavBar
import com.example.ui.common.CloudburstTopAppBar
import com.example.ui.utils.CloudburstContentType
import com.example.ui.utils.CloudburstNavigationType


@Composable
fun CloudburstApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val contentType: CloudburstContentType
    val navigationType: CloudburstNavigationType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = CloudburstContentType.LIST_ONLY
            navigationType = CloudburstNavigationType.BOTTOM_NAVBAR
        }
        WindowWidthSizeClass.Medium -> {
            contentType = CloudburstContentType.LIST_ONLY
            navigationType = CloudburstNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = CloudburstContentType.LIST_AND_DETAIL
            navigationType = CloudburstNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            contentType = CloudburstContentType.LIST_ONLY
            navigationType = CloudburstNavigationType.BOTTOM_NAVBAR
        }
    }

    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Screen.Home.route

    Scaffold(
        topBar = {
            CloudburstTopAppBar(
                currentScreen = currentScreen
            )
        },
        bottomBar = {
            AnimatedVisibility(visible = navigationType == CloudburstNavigationType.BOTTOM_NAVBAR) {
                CloudburstNavBar(
                    currentCategory = ,
                    onTabPressed = {  },
                    modifier = Modifier.fillMaxWidth()
                )
            } },
        containerColor = Color.Transparent
    ) {
        CloudburstBackground(modifier = Modifier.fillMaxSize())

        CloudburstAppContent(
            windowSize = windowSize,
            locationUiState = locationUiState,
            navController = navController,
            navigationType = navigationType,
            contentType = contentType,
            onTabPressed = {},
            modifier = Modifier.padding(it)
        )
    }
}
