package com.example.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.navigation.NavigationItem
import com.example.core.navigation.Screen
import com.example.core.navigation.expandedNavList
import com.example.core.navigation.minimisedNavList
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.RightSideRoundedShape30
import com.example.ui.theme.TopRoundedShape30
import com.example.ui.theme.shadowCustom

/**
 * Renders the content within a permanent navigation drawer, typically used for large screens.
 *
 * This composable iterates through a list of [com.example.core.navigation.NavigationItem]s and displays them as selectable
 * [NavigationDrawerItem]s. It highlights the currently selected item and handles tab press events.
 *
 * @param onTabPressed A lambda function that is invoked when a navigation item is clicked,
 * passing the selected [LocationCategory].
 * @param modifier A [Modifier] to be applied to the content container.
 * @param navItems A list of [com.example.core.navigation.NavigationItem] objects that define the content and routing
 * for each drawer item.
 * @param currentRoute The route of the currently selected [NavigationItem].
 */
@Composable
fun CloudburstNavigationDrawerContent(
    modifier: Modifier = Modifier,
    navItems: List<NavigationItem> = expandedNavList,
    currentRoute: String,
    onTabPressed: (String) -> Unit
) {
    navItems.forEach { navItem ->
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(navItem.labelRes),
                    fontWeight = FontWeight.SemiBold
                )
            },
            icon = {
                Icon(
                    painter = painterResource(navItem.icon),
                    contentDescription = stringResource(navItem.labelRes),
                )
            },
            selected = navItem.route == currentRoute,
            onClick = { onTabPressed(navItem.route) }, // when you press a tab you pass up its navItem route
            colors = NavigationDrawerItemDefaults
                .colors(
                    selectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                ),
            modifier = modifier
        )
    }
}

/**
 * A vertical navigation rail, designed for medium-sized screens like tablets in portrait mode.
 *
 * It displays a series of icons for top-level navigation destinations, highlighting the
 * currently active one.
 *
 * @param onTabPressed A callback invoked with the new [LocationCategory] when an item is pressed.
 * @param modifier A [Modifier] to be applied to the [NavigationRail].
 * @param navItems The list of [NavigationItem]s to be displayed.
 * @param currentRoute The route of the currently active [NavigationItem].
 */
@Composable
fun CloudburstNavigationRail(
    modifier: Modifier = Modifier,
    navItems: List<NavigationItem> = minimisedNavList,
    currentRoute: String,
    onTabPressed: (String) -> Unit,
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .shadowCustom(
                offsetX = dimensionResource(id = R.dimen.navbar_item_horizontal_padding),
                blurRadius = dimensionResource(id = R.dimen.shadow_radius_standard),
                shapeRadius = dimensionResource(id = R.dimen.shadow_shape_radius),
                color = Color.LightGray
            )
            .clip(RightSideRoundedShape30)
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.navdrawer_spacer_height)))
        minimisedNavList.forEach { navItem ->
            NavigationRailItem(
                selected = navItem.route == currentRoute,
                icon = {
                    Icon(
                        painter = painterResource(navItem.icon),
                        contentDescription = stringResource(navItem.labelRes)
                    )
                },
                onClick = { onTabPressed(navItem.route) },
                colors = NavigationRailItemDefaults.colors().copy(
                    selectedIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    }
}

/**
 * A bottom navigation bar with custom styling, intended for compact screens like phones.
 *
 * This composable features a custom shape, shadow, and color scheme. It displays both an icon
 * and a label for each navigation destination.
 *
 * @param onTabPressed A lambda that fires when a user taps on a navigation item.
 * @param modifier A [Modifier] to be applied to the [NavigationBar].
 * @param navItems The list of [NavigationItem]s to populate the bar.
 * @param currentRoute The route of the currently active [NavigationItem].
 */
@Composable
fun CloudburstNavBar(
    modifier: Modifier = Modifier,
    navItems: List<NavigationItem> = minimisedNavList,
    currentRoute: String,
    onTabPressed: (String) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .shadowCustom(
                offsetY = dimensionResource(id = R.dimen.shadow_offset_negative_y),
                blurRadius = dimensionResource(id = R.dimen.shadow_radius_standard),
                shapeRadius = dimensionResource(id = R.dimen.shadow_shape_radius),
                color = Color.LightGray
            )
            .clip(TopRoundedShape30)
    ) {
        navItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = { onTabPressed(navItem.route) },
                icon = {
                    Icon(
                        painter = painterResource(navItem.icon),
                        contentDescription = stringResource(navItem.labelRes)
                    )
                },
                label = {
                    Text(
                        text = stringResource(navItem.labelRes),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIndicatorColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.navbar_item_horizontal_padding))
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewNavBar() {
    CloudburstTheme {
        CloudburstNavBar(
            currentRoute = Screen.Home.route,
            onTabPressed = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewNavRail() {
    CloudburstTheme {
        CloudburstNavigationRail(
            currentRoute = Screen.Home.route,
            onTabPressed = {}
        )
    }
}

