package com.example.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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


/**
 * Renders the main content area of the app based on the selected navigation type.
 *
 * This composable arranges the primary UI elements, such as the navigation rail or
 * permanent drawer, alongside the main content host ([CloudburstNavHost]).
 *
 * @param windowSize The current window size class.
 * @param locationUiState The state object from the ViewModel.
 * @param navController The navigation controller.
 * @param navigationType The determined navigation strategy (e.g., Rail, Drawer).
 * @param contentType The determined content strategy (e.g., List only).
 * @param onTabPressed A lambda function to handle navigation item presses.
 * @param modifier A [Modifier] to be applied to the root layout.
 */
@Composable
fun CloudburstAppContent(
    windowSize: WindowWidthSizeClass,
    locationUiState: LocationUiState,
    navController: NavHostController,
    navigationType: CloudburstNavigationType,
    contentType: CloudburstContentType,
    onTabPressed: (LocationCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    when (navigationType) {
        CloudburstNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet (
                        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        drawerShape = RightSideRoundedShape30,
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.navdrawer_spacer_height)))
                        CloudburstNavigationDrawerContent(
                            currentCategory = locationUiState.currentLocationCategory,
                            onTabPressed = onTabPressed,
                            modifier = Modifier.padding(dimensionResource(R.dimen.navdrawer_item_horizontal_padding))
                        )
                    }
                },
                modifier = Modifier
                    .width(dimensionResource(R.dimen.drawer_width))
                    .shadowCustom(
                        offsetY = dimensionResource(R.dimen.shadow_offset_negative_y),
                        blurRadius = dimensionResource(R.dimen.shadow_blur_radius),
                        shapeRadius = dimensionResource(R.dimen.shadow_shape_radius),
                    )
            ) {
                CloudburstNavHost(
                    navController = navController,
                    windowSize = windowSize,
                    modifier = modifier
                )
            }
        }
        else -> {
            Row(modifier = modifier) {
                AnimatedVisibility(visible = navigationType == CloudburstNavigationType.NAVIGATION_RAIL) {
                    CloudburstNavigationRail(
                        currentCategory = locationUiState.currentLocationCategory,
                        onTabPressed = onTabPressed,
                        modifier = Modifier
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CloudburstNavHost(
                        navController = navController,
                        windowSize = windowSize,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}