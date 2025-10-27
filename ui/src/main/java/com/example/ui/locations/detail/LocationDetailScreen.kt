package com.example.ui.locations.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.locations.LocationUiModel
import com.example.ui.theme.CloudburstTheme

@Composable
fun LocationDetailRoute(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: LocationDetailViewModel = hiltViewModel()
) {
    val uiState: LocationDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()


    when {
        uiState.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.error}")
            }
        }
        uiState.location != null -> {
            when (windowSize) {
                WindowWidthSizeClass.Compact -> {
                    LocationDetailScreenCompact(
                        location = uiState.location!!, // Check this!!!
                        onFavouriteClick = { viewModel.toggleLocationFavourite() },
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Medium -> {
                    LocationDetailScreenMedium(
                        location = uiState.location!!,
                        onFavouriteClick = { viewModel.toggleLocationFavourite() },
                        modifier = modifier
                    )
                }
                WindowWidthSizeClass.Expanded -> {
                    LocationDetailScreenExpanded(
                        location = uiState.location!!,
                        onFavouriteClick = { viewModel.toggleLocationFavourite() },
                        modifier = modifier
                    )
                }
                else -> {
                    LocationDetailScreenCompact(
                        location = uiState.location!!,
                        onFavouriteClick = { viewModel.toggleLocationFavourite() },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationDetailScreenCompact(
    location: LocationUiModel,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .width(dimensionResource(R.dimen.detail_screen_card_width))
                .height(dimensionResource(R.dimen.detail_screen_card_height))
        ) {
            Image(
                painter = painterResource(location.imageIdentifier),
                contentDescription = null
            )
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
        imageIdentifier = R.drawable.rest_bg,
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