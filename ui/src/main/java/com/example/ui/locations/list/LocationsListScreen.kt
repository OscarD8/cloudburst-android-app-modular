package com.example.ui.locations.list

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Beenhere
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Beenhere
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.common.ExploreButton
import com.example.ui.common.backgroundCropPresets
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.ExpandedListItemShape
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.ListItemShape
import com.example.ui.theme.shadowCustom

@Composable
fun LocationsListScreen(
    setTopBarTitle: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    onExploreClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val categoryName = viewModel.categoryName?.lowercase()?.replaceFirstChar { it.titlecase() }

    LaunchedEffect(categoryName) {
        if (categoryName != null) {
            setTopBarTitle(categoryName)

        }
    }

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            LocationsListScreenCompact(
                locationsList = uiState.items,
                onExploreClicked = {},
                onClickToExpand = { viewModel.toggleLocationItemExpanded(it)},
                onClickToFavourite = { viewModel.toggleLocationItemFavourite(it) },
                itemBackgroundRes = viewModel.getCategoryImageRes(),
                modifier = modifier
            )

        }
        WindowWidthSizeClass.Medium -> {

        }
        WindowWidthSizeClass.Expanded -> {

        }
        else -> {

        }
    }

}

@Composable
private fun LocationsListScreenCompact(
    onExploreClicked: (Long) -> Unit,
    locationsList: List<LocationUiModel>,
    onClickToExpand: (Long) -> Unit,
    onClickToFavourite: (Long) -> Unit,
    @DrawableRes itemBackgroundRes: Int,
    modifier: Modifier = Modifier
) {

    //  Create the visibility state *once* for the whole screen
    val isVisible = remember {
        MutableTransitionState(false)
    }

    // Trigger the animation only once when the screen first appears
    LaunchedEffect(Unit) {
        isVisible.targetState = true
    }

    LazyColumn (
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        itemsIndexed(locationsList) { index, location ->

            AnimatedVisibility(
                visibleState = isVisible,
                enter = fadeIn(
                    animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
                ),
                exit = fadeOut(),
                modifier = modifier
            ) {
                LocationListItem(
                    onExploreClicked = { onExploreClicked(location.id) },
                    onClickToExpand = { onClickToExpand(location.id) },
                    onClickToFavourite = { onClickToFavourite(location.id) },
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size], // align image by sequence of crop presets
                    locationName = location.name,
                    locationAddress = location.address,
                    itemBackgroundRes = itemBackgroundRes,
                    isExpanded = location.isExpanded,
                    isFavourite = location.isFavourite,
                    isCarbonCapture = location.isCarbonCapturing,
                    rating = location.rating,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.list_item_width))
                        .height(dimensionResource(R.dimen.list_item_height))
                        .animateEnterExit(
                            enter = slideInHorizontally(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetX = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
private fun LocationsListScreenMedium(

) {

}

@Composable
private fun LocationsListScreenExpanded(

) {
}


@Composable
private fun LocationListItem(
    locationName: String,
    locationAddress: String,
    onExploreClicked: (Long) -> Unit,
    cropAlignment: Alignment,
    onClickToExpand: () -> Unit,
    onClickToFavourite: () -> Unit,
    rating: Int,
    isCarbonCapture: Boolean,
    isExpanded: Boolean,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes itemBackgroundRes: Int = R.drawable.list_master_bg,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Card(
            shape = ListItemShape,
            modifier = modifier
                .dropShadow(ListItemShape, shadow = Shadow(
                    radius = dimensionResource(R.dimen.shadow_radius_standard),
                    spread = dimensionResource(R.dimen.shadow_spread_standard),
                    color = Color.Gray,
                    offset = DpOffset(x = 0.dp, dimensionResource(R.dimen.shadow_offset_y))
                    )
                )
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
                            start = dimensionResource(R.dimen.padding_xlarge),
                            end = dimensionResource(R.dimen.padding_xlarge),
                            top = dimensionResource(R.dimen.padding_xlarge)
                        )
                ) {
                    // container for internal text
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .size(
                                width = dimensionResource(R.dimen.list_item_internal_textbox_width),
                                height = dimensionResource(R.dimen.list_item_internal_textbox_height)
                            )
                            .clip(ListItemInternalText)
                            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                            .innerShadow(
                                shape = ListItemInternalText,
                                shadow = Shadow(
                                    radius = dimensionResource(R.dimen.shadow_radius_standard),
                                    spread = dimensionResource(R.dimen.shadow_spread_standard),
                                    color = Color.Gray,
                                    offset = DpOffset(
                                        x = 0.dp,
                                        0.dp
                                    )
                                )
                            )
                            .padding(
                                vertical = dimensionResource(R.dimen.padding_large),
                                horizontal = dimensionResource(R.dimen.padding_large)
                            )
                    ) {
                        Text(
                            text = locationName,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = locationAddress,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(dimensionResource(R.dimen.list_item_expand_box))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .innerShadow(
                                shape = ListItemInternalText,
                                shadow = Shadow(
                                    radius = dimensionResource(R.dimen.shadow_radius_standard),
                                    spread = dimensionResource(R.dimen.shadow_spread_standard),
                                    color = Color.Gray,
                                    offset = DpOffset(
                                        x = 0.dp,
                                        0.dp
                                    )
                                )
                            )
                    ) {
                        IconButton(
                            onClick = { onClickToExpand() },
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
            }
        }
        if (isExpanded) {
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
                Column (
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large))
                ) {
                    // Rating and bookmark
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.expanded_item_rating),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
                            )
                            repeat(5) {
                                Icon(
                                    imageVector = if (it < rating) Icons.Filled.Brightness7 else Icons.Default.Brightness5,
                                    contentDescription = stringResource(R.string.list_location_rating_desc, rating),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(dimensionResource(R.dimen.list_item_rating_icon_size))
                                )
                            }
                        }
                        IconButton(
                            onClick = onClickToFavourite
                        ) {
                            Icon(
                                imageVector = if (!isFavourite) Icons.Outlined.BookmarkAdd else Icons.Default.BookmarkAdded,
                                contentDescription = stringResource(R.string.expanded_item_favourite_icon_desc),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                        }
                    }
                    // Carbon Capture
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.expanded_item_carbon_capture),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            imageVector = if (isCarbonCapture) Icons.Filled.Beenhere else Icons.Outlined.HeartBroken,
                            contentDescription = stringResource(R.string.list_location_carbon_desc, isCarbonCapture),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.list_item_carbon_item_size))
                                .padding(start = dimensionResource(R.dimen.padding_small))
                        )
                    }
                   Row (
                       horizontalArrangement = Arrangement.Center,
                       modifier = Modifier.fillMaxWidth()
                   ) {
                       ExploreButton(
                           onClick = {},
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
    }
}


@Preview (showBackground = true)
@Composable
private fun PreviewLocationListItem() {
    CloudburstTheme {
        LocationListItem(
            locationName = "Cool Volcano",
            locationAddress = "Volcanoes",
            onExploreClicked = {},
            onClickToExpand = {},
            onClickToFavourite = {},
            isExpanded = true,
            isFavourite = false,
            cropAlignment = Alignment.Center,
            itemBackgroundRes = R.drawable.list_master_bg,
            rating = 2,
            isCarbonCapture = true,
            modifier = Modifier
                .width(dimensionResource(R.dimen.list_item_width))
                .height(dimensionResource(R.dimen.list_item_height))
        )
    }
}
