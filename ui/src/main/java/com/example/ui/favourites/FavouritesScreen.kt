package com.example.ui.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.common.backgroundCropPresets
import com.example.ui.common.components.toggle
import com.example.ui.locations.LocationUiModel
import com.example.ui.locations.list.LocationListItem
import com.example.ui.theme.CloudburstTheme
import com.example.core.navigation.R as navR

@Composable
fun FavouritesScreen(
    setTopBarTitle: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    onExploreClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    var expandedItemIds by remember { mutableStateOf(emptySet<Long>()) }
    val title = stringResource(navR.string.favourites_header)

    LaunchedEffect(Unit) {
        setTopBarTitle(title)
    }

    when {
        uiState.isLoading -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                Text(stringResource(uiState.error!!))
            }
        }

        uiState.items.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.favourites_none))
            }
        }

        else -> {
            val favouritesMap = viewModel.categoriseFavourites()
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {
                    FavouritesScreenCompact(
                        favouritesMap = favouritesMap,
                        expandedItemIds = expandedItemIds,
                        onExploreClicked = { onExploreClicked(it) },
                        onFavouriteClicked = { viewModel.toggleFavourite(it) },
                        onClickToExpand = { expandedItemIds = expandedItemIds.toggle(it) },
                        modifier = modifier
                    )
                }

                WindowWidthSizeClass.Medium -> {
                    FavouritesScreenMedium(
                        favouritesMap = favouritesMap,
                        expandedItemIds = expandedItemIds,
                        onExploreClicked = { onExploreClicked(it) },
                        onFavouriteClicked = { viewModel.toggleFavourite(it) },
                        onClickToExpand = { expandedItemIds = expandedItemIds.toggle(it) },
                        modifier = modifier
                    )
                }

                WindowWidthSizeClass.Expanded -> {
                    FavouritesScreenExpanded(
                        favouritesMap = favouritesMap,
                        expandedItemIds = expandedItemIds,
                        onExploreClicked = { onExploreClicked(it) },
                        onFavouriteClicked = { viewModel.toggleFavourite(it) },
                        onClickToExpand = { expandedItemIds = expandedItemIds.toggle(it) },
                        modifier = modifier
                    )
                }

                else -> {
                    FavouritesScreenCompact(
                        favouritesMap = favouritesMap,
                        expandedItemIds = expandedItemIds,
                        onExploreClicked = { onExploreClicked(it) },
                        onFavouriteClicked = { viewModel.toggleFavourite(it) },
                        onClickToExpand = { expandedItemIds = expandedItemIds.toggle(it) },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun FavouritesScreenCompact(
    favouritesMap: Map<LocationCategory, List<LocationUiModel>>,
    expandedItemIds: Set<Long>,
    onExploreClicked: (Long) -> Unit,
    onFavouriteClicked: (Long) -> Unit,
    onClickToExpand: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        var index = 0

        for ((category, locations) in favouritesMap) {
            CategoryHeader(
                text = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_large))
            )
            locations.forEach { location ->
                LocationListItem(
                    location = location,
                    isExpanded = location.id in expandedItemIds,
                    onClickToExpand = { onClickToExpand(location.id) },
                    onExploreClicked = { onExploreClicked(location.id) },
                    onClickToFavourite = { onFavouriteClicked(location.id) },
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size],
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.list_item_width))
                        .height(dimensionResource(R.dimen.list_item_height))
                )
                index++
            }
        }
    }

}

@Composable
private fun FavouritesScreenMedium(
    favouritesMap: Map<LocationCategory, List<LocationUiModel>>,
    expandedItemIds: Set<Long>,
    onExploreClicked: (Long) -> Unit,
    onFavouriteClicked: (Long) -> Unit,
    onClickToExpand: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        var index = 0

        for ((category, locations) in favouritesMap) {
            CategoryHeader(
                text = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_large))
            )
            locations.forEach { location ->
                LocationListItem(
                    location = location,
                    isExpanded = location.id in expandedItemIds,
                    onClickToExpand = { onClickToExpand(location.id) },
                    onExploreClicked = { onExploreClicked(location.id) },
                    onClickToFavourite = { onFavouriteClicked(location.id) },
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size],
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.list_item_width_medium))
                        .height(dimensionResource(R.dimen.list_item_height_medium))
                )
                index++
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FavouritesScreenExpanded(
    favouritesMap: Map<LocationCategory, List<LocationUiModel>>,
    expandedItemIds: Set<Long>,
    onExploreClicked: (Long) -> Unit,
    onFavouriteClicked: (Long) -> Unit,
    onClickToExpand: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        var index = 0

        for ((category, locations) in favouritesMap) {
            CategoryHeader(
                text = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_large))
            )
            // Use FlowRow to create a wrapping grid-like layout
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                locations.forEach { location ->
                    LocationListItem(
                        location = location,
                        isExpanded = location.id in expandedItemIds,
                        onClickToExpand = { onClickToExpand(location.id) },
                        onExploreClicked = { onExploreClicked(location.id) },
                        onClickToFavourite = { onFavouriteClicked(location.id) },
                        cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size],
                        modifier = Modifier
                            // Use compact dimensions for grid items
                            .width(dimensionResource(R.dimen.list_item_width))
                            .height(dimensionResource(R.dimen.list_item_height))
                    )
                    index++
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(text: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCompactFavourites() {
    val location1 = LocationUiModel(
        id = 1,
        name = "Cool Volcano",
        address = "Volcanoes",
        isFavourite = true,
        rating = 2,
        isCarbonCapturing = true,
        imageIdentifier = R.drawable.list_master_bg,
        category = LocationCategory.RESTAURANTS,
        description = "Cool"
    )
    val location2 = LocationUiModel(
        id = 2,
        name = "Cool Volcano",
        address = "Volcanoes",
        isFavourite = true,
        rating = 2,
        isCarbonCapturing = true,
        imageIdentifier = R.drawable.list_master_bg,
        category = LocationCategory.PARKS,
        description = "Cool"
    )
    val location3 = LocationUiModel(
        id = 3,
        name = "Cool Volcano",
        address = "Volcanoes",
        isFavourite = false,
        rating = 2,
        isCarbonCapturing = true,
        imageIdentifier = R.drawable.list_master_bg,
        category = LocationCategory.RESTAURANTS,
        description = "Cool"
    )
    val mockFavourites: Map<LocationCategory, List<LocationUiModel>> = mapOf(
        LocationCategory.RESTAURANTS to listOf(location1, location3),
        LocationCategory.PARKS to listOf(location2)
    )

    CloudburstTheme {
        FavouritesScreenCompact(
            favouritesMap = mockFavourites,
            expandedItemIds = emptySet(),
            onExploreClicked = {},
            onFavouriteClicked = {},
            onClickToExpand = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}