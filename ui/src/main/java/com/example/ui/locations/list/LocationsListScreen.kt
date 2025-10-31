package com.example.ui.locations.list

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.common.backgroundCropPresets
import com.example.ui.common.components.CarbonRatingRow
import com.example.ui.common.components.ExploreButton
import com.example.ui.common.components.FavouriteIconButton
import com.example.ui.common.components.RatingRow
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.ExpandedListItemShape
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.ListItemShape
import com.example.ui.theme.presetContainerShading
import com.example.ui.theme.presetDropShadow
import com.example.ui.common.components.toggle

@Composable
fun LocationsListScreen(
    setTopBarTitle: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    onExploreClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val categoryName = viewModel.categoryName.lowercase().replaceFirstChar { it.titlecase() }
    var expandedItemIds by remember { mutableStateOf(emptySet<Long>()) }


    LaunchedEffect(categoryName) {
        setTopBarTitle(categoryName)
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
                Text(text = "No locations found in this category.")
            }
        }

        else -> {
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {
                    LocationsListScreenCompact(
                        locationsList = uiState.items,
                        expandedIds = expandedItemIds,
                        onExploreClicked = onExploreClicked,
                        onClickToExpand = { locationId ->
                            expandedItemIds = expandedItemIds.toggle(locationId)
                        },
                        onClickToFavourite = { viewModel.toggleLocationItemFavourite(it) },
                        itemBackgroundRes = viewModel.getCategoryImageRes(),
                        modifier = modifier
                    )
                }

                WindowWidthSizeClass.Medium -> {
                    LocationsListScreenMedium(
                        locationsList = uiState.items,
                        expandedIds = expandedItemIds,
                        onExploreClicked = onExploreClicked,
                        onClickToExpand = { locationId ->
                            expandedItemIds = expandedItemIds.toggle(locationId)
                        },
                        onClickToFavourite = { viewModel.toggleLocationItemFavourite(it) },
                        itemBackgroundRes = viewModel.getCategoryImageRes(),
                        modifier = modifier
                    )
                }

                WindowWidthSizeClass.Expanded -> {
                    LocationsListScreenExpanded(
                        locationsList = uiState.items,
                        expandedIds = expandedItemIds,
                        onExploreClicked = onExploreClicked,
                        onClickToExpand = { locationId ->
                            expandedItemIds = expandedItemIds.toggle(locationId)
                        },
                        onClickToFavourite = { viewModel.toggleLocationItemFavourite(it) },
                        itemBackgroundRes = viewModel.getCategoryImageRes(),
                        modifier = modifier
                    )
                }

                else -> {
                    LocationsListScreenCompact(
                        locationsList = uiState.items,
                        expandedIds = expandedItemIds,
                        onExploreClicked = onExploreClicked,
                        onClickToExpand = { locationId ->
                            expandedItemIds = expandedItemIds.toggle(locationId)
                        },
                        onClickToFavourite = { viewModel.toggleLocationItemFavourite(it) },
                        itemBackgroundRes = viewModel.getCategoryImageRes(),
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationsListScreenCompact(
    onExploreClicked: (Long) -> Unit,
    locationsList: List<LocationUiModel>,
    expandedIds: Set<Long>,
    onClickToExpand: (Long) -> Unit,
    onClickToFavourite: (Long) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes itemBackgroundRes: Int
) {

    //  Create the visibility state *once* for the whole screen
    val isVisible = remember {
        MutableTransitionState(false)
    }

    // Trigger the animation only once when the screen first appears
    LaunchedEffect(Unit) {
        isVisible.targetState = true
    }

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.padding_small),
            vertical = dimensionResource(R.dimen.padding_large)
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        itemsIndexed(locationsList) { index, location ->

            LocationListItem(
                location = location,
                isExpanded = location.id in expandedIds,
                onExploreClicked = { onExploreClicked(location.id) },
                onClickToExpand = { onClickToExpand(location.id) },
                onClickToFavourite = { onClickToFavourite(location.id) },
                cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size], // align image by sequence of crop presets
                itemBackgroundRes = itemBackgroundRes,
                modifier = Modifier
                    .width(dimensionResource(R.dimen.list_item_width))
                    .height(dimensionResource(R.dimen.list_item_height))
            )
        }
    }
}

@Composable
private fun LocationsListScreenMedium(

    onExploreClicked: (Long) -> Unit,
    locationsList: List<LocationUiModel>,
    expandedIds: Set<Long>,
    onClickToExpand: (Long) -> Unit,
    onClickToFavourite: (Long) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes itemBackgroundRes: Int
) {
//  Create the visibility state *once* for the whole screen
    val isVisible = remember {
        MutableTransitionState(false)
    }

    // Trigger the animation only once when the screen first appears
    LaunchedEffect(Unit) {
        isVisible.targetState = true
    }

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.padding_small),
            vertical = dimensionResource(R.dimen.padding_large)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
        modifier = modifier
    ) {
        itemsIndexed(locationsList, key = { _, location -> location.id }) { index, location ->

            AnimatedVisibility(
                visibleState = isVisible,
                modifier = modifier
            ) {
                LocationListItem(
                    location = location,
                    isExpanded = location.id in expandedIds,
                    onExploreClicked = { onExploreClicked(location.id) },
                    onClickToExpand = { onClickToExpand(location.id) },
                    onClickToFavourite = { onClickToFavourite(location.id) },
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size],
                    itemBackgroundRes = itemBackgroundRes,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.list_item_width_medium))
                        .height(dimensionResource(R.dimen.list_item_height_medium))
                )
            }
        }
    }
}

@Composable
private fun LocationsListScreenExpanded(
    onExploreClicked: (Long) -> Unit,
    locationsList: List<LocationUiModel>,
    expandedIds: Set<Long>,
    onClickToExpand: (Long) -> Unit,
    onClickToFavourite: (Long) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes itemBackgroundRes: Int
) {
    val isVisible = remember {
        MutableTransitionState(false)
    }

    LaunchedEffect(Unit) {
        isVisible.targetState = true
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(R.dimen.padding_large),
            vertical = dimensionResource(R.dimen.padding_large)
        ),
        // Add spacing between the grid items
        verticalItemSpacing = dimensionResource(R.dimen.padding_xlarge),
        horizontalArrangement = Arrangement.Center
    ) {
        itemsIndexed(locationsList, key = { _, location -> location.id }) { index, location ->

            AnimatedVisibility(
                visibleState = isVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                LocationListItem(
                    location = location,
                    isExpanded = location.id in expandedIds,
                    onExploreClicked = { onExploreClicked(location.id) },
                    onClickToExpand = { onClickToExpand(location.id) },
                    onClickToFavourite = { onClickToFavourite(location.id) },
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size],
                    itemBackgroundRes = itemBackgroundRes,
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.list_item_height))
                )
            }
        }
    }
}

@Composable
internal fun LocationListItem(
    location: LocationUiModel,
    isExpanded: Boolean,
    onExploreClicked: () -> Unit,
    onClickToExpand: () -> Unit,
    onClickToFavourite: () -> Unit,
    modifier: Modifier = Modifier,
    cropAlignment: Alignment = Alignment.Center,
    @DrawableRes itemBackgroundRes: Int = R.drawable.list_master_bg,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = DampingRatioLowBouncy
                )
            )
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Card(
            shape = ListItemShape,
            modifier = modifier
                .presetDropShadow(ListItemShape)
                .zIndex(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(itemBackgroundRes),
                    contentScale = ContentScale.Crop,
                    alignment = cropAlignment,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Row( // wrapping internal card content
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = dimensionResource(R.dimen.padding_large),
                            end = dimensionResource(R.dimen.padding_large),
                            top = dimensionResource(R.dimen.padding_xlarge)
                        )
                ) {
                    LocationInfo(location)
                    Spacer(modifier = Modifier.weight(1f))
                    ExpandButton(isExpanded, onClickToExpand, location.name)
                }
            }
        }

        if (isExpanded) {
            ExpandedLocationContent(location, onExploreClicked, onClickToFavourite)
        }
    }
}

@Composable
private fun LocationInfo(location: LocationUiModel) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .width(dimensionResource(R.dimen.list_item_internal_textbox_width))
            .height(dimensionResource(R.dimen.list_item_internal_textbox_height))
            .clip(ListItemInternalText)
            .presetContainerShading(ListItemShape)
            .padding(dimensionResource(R.dimen.padding_large))
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = location.address,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExpandButton(isExpanded: Boolean, onClick: () -> Unit, locationName: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .size(dimensionResource(R.dimen.list_item_expand_box))
            .presetContainerShading(CircleShape)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = if (!isExpanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                contentDescription = stringResource(
                    R.string.category_arrow_content_desc,
                    locationName
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ExpandedLocationContent(
    location: LocationUiModel,
    onExploreClicked: () -> Unit,
    onClickToFavourite: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(dimensionResource(R.dimen.list_item_expanded_height))
            .width(dimensionResource(R.dimen.list_item_expanded_width))
            .clip(ExpandedListItemShape)
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .border(
                border = BorderStroke(
                    width = dimensionResource(R.dimen.list_item_expand_box_border),
                    color = MaterialTheme.colorScheme.outlineVariant
                ),
                shape = ExpandedListItemShape
            )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RatingRow(location)
                FavouriteIconButton(location, onClickToFavourite)
            }

            CarbonRatingRow(location)

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExploreButton(
                    onClick = onExploreClicked,
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.padding_medium)),
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    iconSize = 20.dp
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewLocationListItem() {
    val location = LocationUiModel(
        id = 1,
        name = "Cool Volcano",
        address = "Volcanoes",
        isFavourite = false,
        rating = 2,
        isCarbonCapturing = true,
        imageIdentifier = R.drawable.list_master_bg,
        category = LocationCategory.RESTAURANTS,
        description = "Cool"
    )
    CloudburstTheme {
        LocationListItem(
            location = location,
            isExpanded = true,
            onExploreClicked = {},
            onClickToExpand = {},
            onClickToFavourite = {},
            cropAlignment = Alignment.Center,
            itemBackgroundRes = R.drawable.list_master_bg,
            modifier = Modifier
                .width(dimensionResource(R.dimen.list_item_width))
                .height(dimensionResource(R.dimen.list_item_height))
        )
    }
}
