package com.example.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.common.components.ExploreButton
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.FullRoundedShape30
import com.example.ui.theme.presetDropShadow
import com.example.ui.theme.shadowCustom


@Composable
fun CloudburstHomeScreen(
    setTopBarTitle: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    onExploreClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeTitle = stringResource(R.string.home)

    LaunchedEffect(Unit) {
        setTopBarTitle(homeTitle)
        viewModel.eventFlow.collect { randomId ->
            onExploreClicked(randomId)
        }
    }

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompact(
                onExploreClicked = { viewModel.onExploreClicked() },
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            HomeScreenMedium(
                onExploreClicked = { viewModel.onExploreClicked() },
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            HomeScreenExpanded(
                onExploreClicked = { viewModel.onExploreClicked() },
                modifier = modifier
            )
        } else -> {
            HomeScreenCompact(
                onExploreClicked = { viewModel.onExploreClicked() },
                modifier = modifier
            )
        }
    }
}

@Composable
internal fun HomeScreenCompact(
    onExploreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        HomeImage(
            modifier = Modifier
                .layoutId("image")
                .clip(FullRoundedShape30)
                .fillMaxWidth(0.7f)
        )
        HomeTextBox(
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded },
            onExploreClicked = onExploreClicked,
            modifier = Modifier
                .layoutId("textbox")
                .fillMaxWidth(0.9f)
        )
    }
}

@Composable
internal fun HomeScreenMedium(
    onExploreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        HomeImage(
            modifier = Modifier
                .layoutId("image")
                .clip(FullRoundedShape30)
                .fillMaxWidth(0.6f)
        )
        HomeTextBox(
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded },
            onExploreClicked = onExploreClicked,
            modifier = Modifier
                .layoutId("textbox")
                .fillMaxWidth(0.8f)

        )
    }
}

@Composable
internal fun HomeScreenExpanded(
    onExploreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HomeImage(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.9f)
                .padding(start = dimensionResource(R.dimen.padding_xlarge))
                .presetDropShadow(FullRoundedShape30)
        )
        HomeTextBox(
            isExpanded = true,
            onExpandClicked = { },
            onExploreClicked = onExploreClicked,
            isExpandable = false,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth(0.6f)
                .padding(end = dimensionResource(R.dimen.padding_xlarge))
        )
    }
}


@Composable
private fun HomeImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.home_cover_image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(FullRoundedShape30)
    )
}

@Composable
private fun HomeTextBox(
    isExpanded: Boolean,
    onExpandClicked: () -> Unit,
    onExploreClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isExpandable: Boolean = true
) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(R.dimen.home_surface_textbox_shape)),
        color = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = modifier
            .padding(dimensionResource(R.dimen.home_surface_textbox_padding))
            .shadowCustom(
                offsetY = dimensionResource(R.dimen.shadow_offset_y),
                blurRadius = dimensionResource(R.dimen.shadow_radius_standard),
                shapeRadius = dimensionResource(R.dimen.home_surface_textbox_shape),
            )
    ) {
        Column(
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
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.home_text_textbox_padding),
                        bottom = dimensionResource(R.dimen.padding_large)
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (isExpandable) {
                IconButton(onClick = onExpandClicked) {
                    Icon(
                        imageVector = if (!isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(R.string.expand_content_desc),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            ExploreButton(onClick = onExploreClicked)
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
@Preview(showBackground = true)
fun PreviewHomeScreenCompactLight() {
    CloudburstTheme {
        HomeScreenCompact(
            modifier = Modifier.fillMaxSize(),
            onExploreClicked = {
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreenCompactDark() {
    CloudburstTheme(darkTheme = true) {
        HomeScreenCompact(
            modifier = Modifier.fillMaxSize(),
            onExploreClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 800, heightDp = 1280)
fun PreviewHomeScreenMedium() {
    CloudburstTheme {
        HomeScreenMedium(
            modifier = Modifier.fillMaxSize(),
            onExploreClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
fun PreviewHomeScreenExpanded() {
    CloudburstTheme {
        HomeScreenExpanded(
            modifier = Modifier.fillMaxSize(),
            onExploreClicked = {}
        )
    }
}