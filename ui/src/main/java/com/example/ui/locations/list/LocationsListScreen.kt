package com.example.ui.locations.list

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.model.LocationCategory
import com.example.ui.R
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
                onClickToExpand = {},
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
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
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
                    onExploreClicked = {onExploreClicked(location.id)},
                    onClickToExpand = {onClickToExpand(location.id)},
                    isExpanded = location.isExpanded,
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size], // align image by sequence of crop presets
                    locationName = location.name,
                    locationAddress = location.address,
                    itemBackgroundRes = itemBackgroundRes,
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
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes itemBackgroundRes: Int = R.drawable.list_master_bg,
) {
    Card (
        shape = ListItemShape,
        modifier = modifier
            .dropShadow(ListItemShape, shadow = Shadow(
                radius = dimensionResource(R.dimen.shadow_radius_standard),
                spread = dimensionResource(R.dimen.shadow_spread_standard),
                color = Color.Gray,
                offset = DpOffset(x = 0.dp, dimensionResource(R.dimen.shadow_offset_y))
                )
            )
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
                Column ( // container for internal text
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
                                offset = DpOffset(x = 0.dp, dimensionResource(R.dimen.shadow_offset_y))
                            )
                        )
                        .padding(
                            vertical = dimensionResource(R.dimen.padding_large),
                            horizontal = dimensionResource(R.dimen.padding_large)
                        )
                ) {
                    Text(
                        text = locationName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = locationAddress,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensionResource(R.dimen.list_item_expand_box))
                        .background(color = MaterialTheme.colorScheme.onSurface)
                        .shadowCustom(
                            blurRadius = dimensionResource(R.dimen.shadow_radius_standard),
                            shapeRadius = dimensionResource(R.dimen.shadow_inner_circle),
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                ) {
                    IconButton (
                        onClick = { onClickToExpand() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = if (!isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.category_arrow_content_desc, locationName),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ListItemExpandedCard(
    rating: Int,
    isCarbonCapture: Boolean,
    modifier: Modifier = Modifier
) {
    Card (
        shape = ExpandedListItemShape,
        modifier = modifier
            .width(dimensionResource(R.dimen.list_item_expanded_width))
            .height(dimensionResource(R.dimen.list_item_expanded_height)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inverseOnSurface)
    ) {

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
            isExpanded = false,
            cropAlignment = Alignment.Center,
            itemBackgroundRes = R.drawable.list_master_bg,
            modifier = Modifier
                .width(dimensionResource(R.dimen.list_item_width))
                .height(dimensionResource(R.dimen.list_item_height))
        )
    }
}
