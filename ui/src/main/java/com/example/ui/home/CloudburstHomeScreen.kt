package com.example.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.ui.R
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.FullRoundedShape30
import com.example.ui.theme.shadowCustom


@Composable
fun CloudburstHomeScreen(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompact(modifier = modifier)
        }
        WindowWidthSizeClass.Medium -> {
            HomeScreenMedium()
        }
        WindowWidthSizeClass.Expanded -> {
            HomeScreenExpanded()
        } else -> {
            HomeScreenCompact()
        }
    }
}

@Composable
internal fun HomeScreenCompact(
    modifier: Modifier = Modifier
) {
    // Decided not to hoist state because the screen contains minimal logic which is
    // all self-contained here
    var isExpanded by remember { mutableStateOf(false) }
    val constraints = if (isExpanded) expandedConstraints else collapsedConstraints

    ConstraintLayout(
        constraintSet = constraints,
        animateChangesSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        modifier = modifier
    ) {

        Image(
            painter = painterResource(R.drawable.home_cover_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("image")
                .clip(FullRoundedShape30)
                .fillMaxWidth(0.7f)
        )

        Surface(
            shape = RoundedCornerShape(dimensionResource(R.dimen.home_surface_textbox_shape)),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier
                .layoutId("textbox")
                .padding(dimensionResource(R.dimen.home_surface_textbox_padding))
                .fillMaxWidth(0.9f)
                .shadowCustom(
                    offsetY = dimensionResource(R.dimen.shadow_offset_y),
                    blurRadius = dimensionResource(R.dimen.shadow_blur_radius),
                    shapeRadius = dimensionResource(R.dimen.home_surface_textbox_shape),
                )
        ) {
            Column (
                modifier = Modifier.padding(dimensionResource(R.dimen.home_column_textbox_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.home_welcome),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                AnimatedVisibility(visible = isExpanded) {
                    Text(
                        text = stringResource(R.string.home_city_description),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.home_text_textbox_padding)),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(onClick = {isExpanded = !isExpanded}) {
                    Icon(
                        imageVector = if (!isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(R.string.expand_content_desc),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Button(
                    onClick = {}, //TODO
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline),
                    elevation = ButtonDefaults.buttonElevation(dimensionResource(R.dimen.button_standard_elevation))
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.atr_icon),
                            contentDescription = stringResource(R.string.explore_button),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        Text(
                            text = stringResource(R.string.explore_button),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            modifier = Modifier.padding(
                                start = dimensionResource(R.dimen.button_text_start_padding),
                                bottom = dimensionResource(R.dimen.button_text_bottom_padding)
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * A decoupled [ConstraintSet] that defines the layout for the **collapsed** state of the home screen.
 *
 * In this state:
 * - The `textbox` is positioned in the vertical center with a lower bias (0.6f).
 * - The `image` is pinned to the bottom of the `textbox`, creating a compact, layered look.
 */
private val collapsedConstraints = ConstraintSet {
    val textboxRef = createRefFor("textbox")
    val imageRef = createRefFor("image")

    // Centered Lower
    constrain(textboxRef) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        verticalBias = 0.6f
    }

    // Constrain image to the bottom of the textbox
    constrain(imageRef) {
        bottom.linkTo(textboxRef.bottom, margin = 40.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

/**
 * A decoupled [ConstraintSet] that defines the layout for the **expanded** state of the home screen.
 *
 * In this state:
 * - The `textbox` is biased towards the bottom of the screen (0.9f) to accommodate its expanded content.
 * - The `image` moves independently to the top of the screen.
 *
 * This set is used in conjunction with `collapsedConstraints` and the `animateChangesSpec' parameter
 * on a [ConstraintLayout] to create a smooth transition.
 */
private val expandedConstraints = ConstraintSet {
    val textboxRef = createRefFor("textbox")
    val imageRef = createRefFor("image")

    // Centered higher
    constrain(textboxRef) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        verticalBias = 0.9f
    }

    constrain(imageRef) {
        top.linkTo(parent.top, margin = 16.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

@Composable
internal fun HomeScreenMedium() {

}

@Composable
internal fun HomeScreenExpanded() {

}

@Composable
@Preview (showBackground = true)
fun PreviewHomeScreenCompactLight() {
    CloudburstTheme {
        HomeScreenCompact(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview (showBackground = true)
fun PreviewHomeScreenCompactDark() {
    CloudburstTheme(darkTheme = true) {
        HomeScreenCompact(
            modifier = Modifier.fillMaxSize()
        )
    }
}