package com.example.ui.locations.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.ui.R
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.ExpandedListItemShape
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.ListItemShape
import com.example.ui.theme.shadowCustom

@Composable
fun LocationsListScreen(
    windowSize: WindowWidthSizeClass,
    onExploreClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            LocationsListScreenCompact(
                locationsList = uiState.items,
                onExploreClicked = uiState.,
                isExpanded = viewModel.toggleLocationItemExpanded(),
                onClickToExpand = TODO(),
                modifier = TODO()
            )

        }
        WindowWidthSizeClass.Medium -> {
            LocationsListScreenMedium()
        }
        WindowWidthSizeClass.Expanded -> {
            LocationsListScreenExpanded()
        }
        else -> {
            LocationsListScreenCompact()
        }
    }

}

@Composable
private fun LocationsListScreenCompact(
    onExploreClicked: (Long) -> Unit,
    locationsList: List<LocationUiModel>,
    onClickToExpand: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
    ) {
        itemsIndexed(locationsList) { index, location ->
            LocationListItem(
                onExploreClicked = {onExploreClicked(location.id)},
                onClickToExpand = {onClickToExpand(location.id)},
                isExpanded = location.isExpanded,
                locationName = location.name,
                locationDesc = location.description
            )
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
    locationDesc: String,
    onExploreClicked: (Long) -> Unit,
    onClickToExpand: () -> Unit,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Card (
        shape = ListItemShape,
        modifier = modifier
            .dropShadow(ListItemShape, shadow = Shadow(
                radius = 4.dp,
                spread = 3.dp,
                color = Color.Gray,
                offset = DpOffset(x = 0.dp, 2.dp)
                )
            )
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.list_master_bg),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = dimensionResource(R.dimen.padding_xlarge),
                        end = dimensionResource(R.dimen.padding_xlarge),
                        top = dimensionResource(R.dimen.padding_xlarge)
                    )
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .size(
                            width = dimensionResource(R.dimen.list_item_internal_text_width),
                            height = dimensionResource(R.dimen.list_item_internal_text_height)
                        )
                        .clip(ListItemInternalText)
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                        .innerShadow(
                            shape = RoundedCornerShape(20.dp),
                            shadow = Shadow(
                                radius = 10.dp,
                                spread = 2.dp,
                                color = Color(0x40000000),
                                offset = DpOffset(x = 6.dp, 7.dp)
                            )
                        )
                ) {
                    Text(
                        text = locationName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensionResource(R.dimen.list_item_next_arrow_box))
                        .background(color = MaterialTheme.colorScheme.onSurface)
                        .shadowCustom(
                            blurRadius = 4.dp,
                            shapeRadius = 50.dp,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                ) {
                    IconButton (
                        onClick = { onClickToExpand(!isExpanded) },
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



@Preview
@Composable
private fun PreviewLocationListItem() {
    CloudburstTheme {
        LocationListItem(
            locationName = "Cool Volcano",
            locationDesc = "Volcanoes",
            onExploreClicked = {},
            onClickToExpand = {},
            isExpanded = false,
            modifier = Modifier.fillMaxSize()
        )
    }
}
