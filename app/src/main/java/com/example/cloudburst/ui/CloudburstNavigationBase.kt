package com.example.cloudburst.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.example.cloudburst.navigation.CloudburstNavHost
import com.example.ui.R
import com.example.ui.common.CloudburstNavigationDrawerContent
import com.example.ui.common.CloudburstNavigationRail
import com.example.ui.theme.RightSideRoundedShape30
import com.example.ui.theme.shadowCustom

/**
 * Notes:
 * At this point the hierarchy is:
 * Surface > Scaffold (possible NavBar, Background, TopAppBar). So now on to content.
 */

/**
 * In the context of the app content area - establishes navigation components
 * if their context is out of the scaffold (not a Compact device).
 * Alongside any Nav component implementations, makes the call to compose
 * the app screen composables by calling the NavHost.
 *
 * This composable arranges the primary UI elements, such as the navigation rail or
 * permanent drawer, alongside the main content host ([CloudburstNavHost]).
 *
 * @param windowSize The current window size class. Needed to pass through to Screen Composables.
 * @param navController The navigation controller.
 * @param navigationType The determined navigation strategy (e.g., Rail, Drawer).
 * @param contentType The determined content strategy (e.g., List only).
 * @param onTabPressed A lambda function to handle navigation item presses.
 * @param modifier A [Modifier] to be applied to the root layout.
 */
@Composable
fun CloudburstNavigationBase(
    windowSize: WindowWidthSizeClass,
    setTopBarTitle: (String) -> Unit,
    navController: NavHostController,
    navigationType: CloudburstNavigationType,
    contentType: CloudburstContentType,
    currentRoute: String,
    onTabPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (navigationType) {
        CloudburstNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet (
                        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        drawerShape = RightSideRoundedShape30,
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.drawer_width))
                            .shadowCustom(
                                offsetY = dimensionResource(R.dimen.shadow_offset_negative_y),
                                blurRadius = dimensionResource(R.dimen.shadow_radius_standard),
                                shapeRadius = dimensionResource(R.dimen.shadow_shape_radius)
                            )
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.navdrawer_spacer_height)))
                        CloudburstNavigationDrawerContent(
                            currentRoute = currentRoute,
                            onTabPressed = onTabPressed,
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.navdrawer_item_horizontal_padding))
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                CloudburstNavHost(
                    setTopBarTitle = setTopBarTitle,
                    navController = navController,
                    windowSize = windowSize,
                    modifier = modifier
                )
            }
        }
        CloudburstNavigationType.NAVIGATION_RAIL -> {
            Row(modifier = Modifier.fillMaxSize()) { // Medium device content wraps in a row the rail and the content.
                CloudburstNavigationRail(
                    currentRoute = currentRoute,
                    onTabPressed = onTabPressed, // placeholder to pass route up to AppBase.
//                    modifier = Modifier.fillMaxHeight()
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CloudburstNavHost(
                        setTopBarTitle = setTopBarTitle,
                        navController = navController,
                        windowSize = windowSize,
                        modifier = modifier
                    )
                }
            }
        }
        else -> {
            CloudburstNavHost(
                setTopBarTitle = setTopBarTitle,
                navController = navController,
                windowSize = windowSize,
                modifier = modifier
            )
        }
    }
}