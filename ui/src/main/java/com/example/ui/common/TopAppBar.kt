package com.example.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.theme.BottomRoundedShape30
import com.example.ui.theme.PopUpShape
import com.example.ui.theme.SunriseShape
import com.example.ui.theme.shadowCustom


/**
 * A custom, styled top app bar for the Cloudburst application.
 *
 * Displays the current screen's title and a unique decorative "sunrise" element on the right.
 * It features a custom shadow and a clipped shape for a distinct visual identity.
 *
 * @param currentScreen The [CloudburstScreen] enum for the currently displayed screen.
 * @param modifier A [Modifier] to be applied to the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloudburstTopAppBar(
    screenTitle: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = screenTitle,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        actions = {
            Box{
                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(
                            top = dimensionResource(R.dimen.sunrise_padding_top)
                        )
                        .size(
                            width = dimensionResource(R.dimen.sunrise_shape_width),
                            height = dimensionResource(R.dimen.sunrise_shape_height)
                        ),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = SunriseShape
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = PopUpShape,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.inner_circle_size))
                                .align(Alignment.BottomEnd)
                                .padding(
                                    end = dimensionResource(R.dimen.inner_circle_padding_end),
                                    top = dimensionResource(R.dimen.inner_circle_padding_top)
                                )
                        ) {
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.tertiaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .shadowCustom(
                offsetY = dimensionResource(id = R.dimen.shadow_offset_y),
                blurRadius = dimensionResource(id = R.dimen.shadow_blur_radius),
                shapeRadius = dimensionResource(id = R.dimen.shadow_shape_radius)
            )
            .clip(BottomRoundedShape30)
    )
}