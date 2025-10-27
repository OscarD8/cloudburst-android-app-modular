package com.example.ui.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
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
import com.example.core.navigation.R as NavR
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.ListItemShape
import com.example.ui.theme.shadowCustom

/**
 * A composable that displays a list of categories.
 * On category selection, the [onCategorySelected] lambda is called, and passed up to the NavHost.
 *
 */
@Composable
fun CategoriesScreen(
    windowSize: WindowWidthSizeClass,
    setTopBarTitle: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategorySelected: (LocationCategory) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val categoriesTitle = stringResource(NavR.string.categories_header)
    LaunchedEffect(Unit) { setTopBarTitle(categoriesTitle) }

    /*
    * Used to retrieve random alignments for card backgrounds for each category,
    * to create the effect of multiple images of the city.
     */

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CategoriesScreenCompact(
                categoriesList = uiState.items,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            CategoriesScreenMedium(
                categoriesList = uiState.items,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            CategoriesScreenExpanded(
                categoriesList = uiState.items,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        } else -> {
            CategoriesScreenCompact(
                categoriesList = uiState.items,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
    }

}

/**
 * A composable that displays a list of categories in a compact layout.
 */
@Composable
private fun CategoriesScreenCompact (
    categoriesList: List<LocationCategory>,
    onCategorySelected: (LocationCategory) -> Unit,
    backgroundCropPresets: List<Alignment>,
    modifier: Modifier = Modifier
) {
    val isVisible = remember { MutableTransitionState(false) }

    LaunchedEffect(Unit) {
        isVisible.targetState = true
    }


    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
        modifier = modifier
    ) {
        itemsIndexed(categoriesList) { index, category ->

            AnimatedVisibility(
                visibleState = isVisible,
                enter = fadeIn(
                    animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
                ),
                exit = fadeOut(),
                modifier = modifier
            ) {
                CategoryListItem(
                    category = category,
                    onCategorySelected = onCategorySelected,
                    cropAlignment = backgroundCropPresets[index % backgroundCropPresets.size], // align background in sequence of presets
                    modifier = Modifier
                        .size(
                            width = dimensionResource(R.dimen.list_item_width),
                            height = dimensionResource(R.dimen.list_item_height)
                        )
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_medium)
                        )
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                        .dropShadow(ListItemShape, shadow = Shadow(
                            radius = dimensionResource(R.dimen.shadow_radius_standard),
                            spread = dimensionResource(R.dimen.shadow_spread_standard),
                            color = Color.Gray,
                            offset = DpOffset(x = 0.dp, dimensionResource(R.dimen.shadow_offset_y))
                            )
                        )
                )
            }
        }
    }
}

@Composable
private fun CategoriesScreenMedium (
    categoriesList: List<LocationCategory>,
    onCategorySelected: (LocationCategory) -> Unit,
    backgroundCropPresets: List<Alignment>,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun CategoriesScreenExpanded (
    categoriesList: List<LocationCategory>,
    onCategorySelected: (LocationCategory) -> Unit,
    backgroundCropPresets: List<Alignment>,
    modifier: Modifier = Modifier
) {

}

/**
 * A composable that displays a single category item.
 *
 * @param category The category to display of type LocationCategory.
 * @param onCategorySelected A lambda that is called when the category is selected, passing up its LocationCategory.
 * @param cropAlignment Alignment object for the background image.
 */
@Composable
private fun CategoryListItem(
    category: LocationCategory,
    onCategorySelected: (LocationCategory) -> Unit,
    cropAlignment: Alignment,
    modifier: Modifier = Modifier
) {
    Card (
        shape = ListItemShape,
        modifier = modifier
    ) {
        Box {
            Image( // background
                painter = painterResource(R.drawable.list_master_bg),
                contentScale = ContentScale.Crop,
                alignment = cropAlignment,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Row( // content in card
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = dimensionResource(R.dimen.padding_xlarge),
                        end = dimensionResource(R.dimen.padding_xlarge),
                        top = dimensionResource(R.dimen.padding_xlarge)
                    )
            ) {
                Box ( // container for text of category item
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(
                            width = dimensionResource(R.dimen.list_item_internal_textbox_width_cat),
                            height = dimensionResource(R.dimen.list_item_internal_textbox_height_cat)
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
                ) {
                    Text(
                        text = getTranslatedCategoryName(category),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraLight
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box( // container for clickable icon of category item
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensionResource(R.dimen.list_item_next_arrow_box))
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
                ) {
                    IconButton (
                        onClick = { onCategorySelected(category) },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = stringResource(R.string.category_arrow_content_desc, category),
                        )
                    }
                }
            }
        }
    }
}

/**
 * LocationCategory types are used for identifying all category list items.
 * String representations are used for text display on the cards themselves.
 */
@Composable
private fun getTranslatedCategoryName(category: LocationCategory): String {
    return when (category) {
        LocationCategory.RESTAURANTS -> stringResource(R.string.restaurant_header)
        LocationCategory.CAFES -> stringResource(R.string.cafe_header)
        LocationCategory.PARKS -> stringResource(R.string.park_header)
        LocationCategory.TEMPLES -> stringResource(R.string.temple_header)
        LocationCategory.PRINTERS -> stringResource(R.string.mycelium_printer_header)
    }
}

@Preview
@Composable
fun PreviewCategoryItem() {
    CloudburstTheme {
        CategoryListItem(
            category = LocationCategory.RESTAURANTS,
            onCategorySelected = {},
            cropAlignment = Alignment.TopStart,
            modifier = Modifier
                .size(
                    width = dimensionResource(R.dimen.list_item_width),
                    height = dimensionResource(R.dimen.list_item_height)
                )
        )
    }
}

@Preview
@Composable
fun PreviewCategoryItemDark() {
    CloudburstTheme(darkTheme = true) {
        CategoryListItem(
            category = LocationCategory.RESTAURANTS,
            onCategorySelected = {},
            cropAlignment = Alignment.TopStart,
            modifier = Modifier
                .size(
                    width = dimensionResource(R.dimen.list_item_width),
                    height = dimensionResource(R.dimen.list_item_height)
                )
        )
    }
}
