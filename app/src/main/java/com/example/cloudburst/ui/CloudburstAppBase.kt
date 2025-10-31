package com.example.cloudburst.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cloudburst.R
import com.example.core.navigation.Screen
import com.example.core.navigation.navigationItemMap
import com.example.ui.common.CloudburstBackground
import com.example.ui.common.CloudburstNavBar
import com.example.ui.common.CloudburstTopAppBar
import com.example.cloudburst.MainActivity

/**
 * Sets contentType and navigationType.
 * Establishes the Scaffold for the UI.
 */
@Composable
fun CloudburstAppBase(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val contentType: CloudburstContentType
    val navigationType: CloudburstNavigationType

    /** On any config change or app initialisation, [MainActivity] passes
     * the current window width size to [CloudburstAppBase]. Enums are then
     * assigned, which determine the navigation type wrapping the content
     * and whether the content itself will be for list only or list and detail.
     */
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
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route
    Log.d("CloudburstAppBase", "currentRoute: $currentRoute")


    val appName = stringResource(R.string.app_name)
    var topBarTitle by remember { mutableStateOf(appName) }

    /* Using the current route, we can look up the associated navigation item to
     enable a fetch of the title (label) related to that route. */
    val currentNavItem = navigationItemMap[currentRoute] // this can be null (in which case when fetching title just take app name)
    val currentTitle = currentNavItem?.labelRes  ?: R.string.app_name

    /**
     * Establishing the UI contained in a Scaffold within the Surface established in MainActivity.
     * Bottom bar only on compact devices.
     */
    Scaffold(
        topBar = { // defining to app bar here causes complications for shape layouts of different nav components...
            CloudburstTopAppBar(
                screenTitle = topBarTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                onNavigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            AnimatedVisibility(visible = navigationType == CloudburstNavigationType.BOTTOM_NAVBAR) {
                CloudburstNavBar(
                    currentRoute = currentRoute,
                    onTabPressed = { route -> navController.navigate(route) }, // route passed up from NavItem in NavBar
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        containerColor = Color.Transparent // As working with background wallpaper for the app content.
    ) {
        CloudburstBackground(modifier = Modifier.fillMaxSize())

        CloudburstNavigationBase(
            windowSize = windowSize,
            setTopBarTitle = { title -> topBarTitle = title },
            navController = navController,
            navigationType = navigationType,
            contentType = contentType,
            onTabPressed = { route -> navController.navigate(route)}, // route passed up from NavItem in NavRail or NavDrawer.
            currentRoute = currentRoute,
            modifier = Modifier.padding(it)
        )
    }
}


