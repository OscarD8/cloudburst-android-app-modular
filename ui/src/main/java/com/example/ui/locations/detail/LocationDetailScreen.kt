package com.example.ui.locations.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.common.components.CarbonRatingRow
import com.example.ui.common.components.FavouriteIconButton
import com.example.ui.common.components.RatingRow
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.DetailComponentShape
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.presetContainerShading
import com.example.ui.theme.presetDropShadow

/**
 * Location detail screen accessed when a user clicks on a location in the list.
 * Can also be accessed by pressing Explore from the Home page.
 */
@Composable
fun LocationDetailRoute(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    setTopBarTitle: (String) -> Unit,
    viewModel: LocationDetailViewModel = hiltViewModel()
) {
    val uiState: LocationDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val locationName: String? = uiState.location?.name

    LaunchedEffect(locationName) {
        setTopBarTitle(locationName ?: "")
    }


    LocationDetailStateWrapper(
        uiState = uiState,
        onFavouriteClick = { viewModel.toggleLocationFavourite(it) },
        windowSize = windowSize,
        modifier = modifier
    )
}

/*
 * Notes on why the Wrapper is needed:
 * The Check: The Head Chef leans in and asks the busy Sous Chef (the by delegate), "Is that meal cooked?" (uiState.location != null)
 *
 * The Sous Chef: "Yep, looks cooked to me!"
 *
 * The Problem: The Head Chef then turns to the waiter and says, "Go grab that meal from the Sous Chef." (location = uiState.location)
 *
 * The Compiler's Panic: The Head Chef (compiler) immediately stops the waiter,
 * "Whoa, hold on! By the time you get to the Sous Chef, he might have been handed a new order
 * (a new state emission) and started a new, uncooked meal. The meal you grab might not be the same one I just checked.
 * I cannot guarantee it. I won't allow this."
 *
 * Basically the chef of uiState is using property delegation of a StateFlow that may represent
 * two different objects in-between the null assertion check and the passing of that same
 * state property to the Screen Composable. It seems at runtime this is not actually a problem
 * because the StateFlow has been captured once per composable execution, but the compiler
 * sees property delegation as unstable?
 */

/**
 * Wrapper for the location detail screen to handle state changes of the detail screen loading.
 *
 *
 * @param uiState The current state of the detail screen.
 * @param windowSize The current window size.
 * @param onFavouriteClick The callback to invoke when the favourite button is clicked.
 */
@Composable
private fun LocationDetailStateWrapper(
    uiState: LocationDetailUiState,
    windowSize: WindowWidthSizeClass,
    onFavouriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                Text(stringResource(uiState.error))
            }
        }
        uiState.location != null -> {
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {
                    LocationDetailScreenCompact(
                        location = uiState.location,
                        onFavouriteClick = { onFavouriteClick(uiState.location.id) },
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Medium -> {
                    LocationDetailScreenMedium(
                        location = uiState.location,
                        onFavouriteClick = { onFavouriteClick(uiState.location.id) },
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    LocationDetailScreenExpanded(
                        location = uiState.location,
                        onFavouriteClick = { onFavouriteClick(uiState.location.id) },
                        modifier = modifier
                    )
                }
                else -> {
                    LocationDetailScreenCompact(
                        location = uiState.location,
                        onFavouriteClick = { onFavouriteClick(uiState.location.id) },
                        modifier = modifier
                    )
                }
            }
        }
        else -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * Compact version of the location detail screen.
 *
 * @param location The location to display.
 * @param onFavouriteClick The callback to invoke when the favourite button is clicked.
 * @param modifier The modifier to apply to the screen.
 */
@Composable
private fun LocationDetailScreenCompact(
    location: LocationUiModel,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        constraintSet = locationDetailConstraintSet,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            FavouriteIconButton(location, onFavouriteClick )
        }

        Card(
            shape = DetailComponentShape,
            modifier = Modifier
                .width(dimensionResource(R.dimen.detail_screen_card_width))
                .height(dimensionResource(R.dimen.detail_screen_card_height))
                .layoutId("card")
                .zIndex(1f)
                .presetDropShadow(DetailComponentShape)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box{
                    Image(
                        painter = painterResource(location.imageIdentifier),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.padding_super_extra_large_yes),
                        end = dimensionResource(R.dimen.padding_super_extra_large_yes)
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.detail_card_textbox_width))
                            .height(dimensionResource(R.dimen.detail_card_textbox_height))
                            .clip(ListItemInternalText)
                            .presetContainerShading(DetailComponentShape)
                            .padding(horizontal = dimensionResource(R.dimen.padding_xlarge))

                    ) {
                        Text(
                            text = location.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Text(
                            text = location.address,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Column (
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .height(dimensionResource(R.dimen.detail_screen_info_height))
                .width(dimensionResource(R.dimen.detail_screen_info_width))
                .clip(DetailComponentShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = DetailComponentShape
                )
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_xlarge),
                    vertical = dimensionResource(R.dimen.padding_large)
                )
                .layoutId("column")
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = location.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_xlarge)),
                textAlign = TextAlign.Justify
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.fillMaxWidth()
            ) {
                RatingRow(location)
                CarbonRatingRow(location)
            }
            Spacer(modifier = Modifier.weight(0.25f))
        }
    }
}

private val locationDetailConstraintSet = ConstraintSet {
    val cardRef = createRefFor("card")
    val columnRef = createRefFor("column")

    constrain(cardRef) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        verticalBias = 0.15f
    }

    constrain(columnRef) {
        top.linkTo(cardRef.top, margin = 170.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

@Composable
private fun LocationDetailScreenMedium(
    location: LocationUiModel,
    modifier: Modifier = Modifier,
    onFavouriteClick: () -> Unit
) {

}

@Composable
private fun LocationDetailScreenExpanded(
    location: LocationUiModel,
    modifier: Modifier = Modifier,
    onFavouriteClick: () -> Unit
) {

}

@Preview (showBackground = true)
@Composable
private fun PreviewLocationDetailScreen() {
    val location = LocationUiModel(
        id = 1,
        name = "Volcano",
        address = "Cool Volcano Street",
        description = "This is a volcano you want to see.",
        imageIdentifier = R.drawable.restaurant_mycelia_feast,
        rating = 4,
        isCarbonCapturing = false,
        category = LocationCategory.RESTAURANTS,
        isFavourite = true,
    )
    CloudburstTheme {
        LocationDetailScreenCompact(
            location = location,
            modifier = Modifier.fillMaxSize(),
            onFavouriteClick = {}
        )
    }
}