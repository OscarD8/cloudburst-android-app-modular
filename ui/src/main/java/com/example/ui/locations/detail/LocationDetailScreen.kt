package com.example.ui.locations.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.DetailComponentShape

/**
 * Location detail screen accessed when a user clicks on a location in the list.
 * Can also be accessed by pressing Explore from the Home page.
 */
@Composable
fun LocationDetailRoute(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: LocationDetailViewModel = hiltViewModel()
) {
    val uiState: LocationDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LocationDetailStateWrapper(
        isLoading = uiState.isLoading,
        location = uiState.location,
        error = uiState.error,
        onFavouriteClick = { viewModel.toggleLocationFavourite() },
        windowSize = windowSize,
        modifier = modifier
    )
}

/**
 * Wrapper for the location detail screen to handle state changes of the detail screen loading.
 * If the screen is currently loading, a circular progress indicator is displayed.
 * If the screen is in an error state, an error message is displayed.
 * If the screen is in a success state, the location detail screen for the window size is called.
 *
 * @param isLoading Whether the screen is currently loading.
 * @param location The location to display.
 * @param error The error message to display.
 */
@Composable
private fun LocationDetailStateWrapper(
    isLoading: Boolean,
    location: LocationUiModel?,
    error: String?,
    windowSize: WindowWidthSizeClass,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
                Text(stringResource(R.string.error_location_not_found))
            }
        }
        location != null -> {
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {
                    LocationDetailScreenCompact(
                        location = location,
                        onFavouriteClick = onFavouriteClick,
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Medium -> {
                    LocationDetailScreenMedium(
                        location = location,
                        onFavouriteClick = onFavouriteClick,
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    LocationDetailScreenExpanded(
                        location = location,
                        onFavouriteClick = onFavouriteClick,
                        modifier = modifier
                    )
                }
                else -> {
                    LocationDetailScreenCompact(
                        location = location,
                        onFavouriteClick = onFavouriteClick,
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = DetailComponentShape,
            modifier = Modifier
                .width(dimensionResource(R.dimen.detail_screen_card_width))
                .height(dimensionResource(R.dimen.detail_screen_card_height))
        ) {
            Image(
                painter = painterResource(location.imageIdentifier),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column (
            modifier = Modifier
                .height(dimensionResource(R.dimen.detail_screen_info_height))
                .width(dimensionResource(R.dimen.detail_screen_info_width))
                .clip(DetailComponentShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {

        }
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

@Preview
@Composable
private fun PreviewLocationDetailScreen() {
    val location = LocationUiModel(
        id = 1,
        name = "Volcano",
        address = "Cool Volcano Street",
        description = "This is a volcano you want to see.",
        imageIdentifier = R.drawable.location_detail_card_bg,
        rating = 4,
        isCarbonCapturing = false,
        category = LocationCategory.RESTAURANTS,
        isFavourite = true,
        isExpanded = true
    )
    CloudburstTheme {
        LocationDetailScreenCompact(
            location = location,
            modifier = Modifier.fillMaxSize(),
            onFavouriteClick = {}
        )
    }
}