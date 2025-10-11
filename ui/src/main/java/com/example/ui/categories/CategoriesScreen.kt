package com.example.ui.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.model.LocationCategory
import com.example.ui.R
import com.example.ui.theme.CloudburstTheme
import com.example.ui.theme.ListItemInternalText
import com.example.ui.theme.ListItemShape
import java.util.Locale

@Composable
fun CategoriesScreen(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategorySelected: (LocationCategory) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val backgroundCropPresets = listOf(
        Alignment.TopStart,
        Alignment.Center,
        Alignment.BottomEnd,
        Alignment.CenterStart,
        Alignment.TopEnd
    )

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CategoriesScreenCompact(
                categoriesList = uiState.categories,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            CategoriesScreenMedium(
                categoriesList = uiState.categories,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            CategoriesScreenExpanded(
                categoriesList = uiState.categories,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        } else -> {
            CategoriesScreenCompact(
                categoriesList = uiState.categories,
                onCategorySelected = onCategorySelected,
                backgroundCropPresets = backgroundCropPresets,
                modifier = modifier
            )
        }
    }

}

@Composable
private fun CategoriesScreenCompact (
    categoriesList: List<LocationCategory>,
    onCategorySelected: (LocationCategory) -> Unit,
    backgroundCropPresets: List<Alignment>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
    ) {
        itemsIndexed(categoriesList) { index, category ->
            val alignmentForThisItem = backgroundCropPresets[index % backgroundCropPresets.size]

            CategoryListItem(
                category = category,
                onCategorySelected = onCategorySelected,
                cropAlignment = alignmentForThisItem,
                modifier = Modifier
                    .size(
                        width = dimensionResource(R.dimen.list_item_width),
                        height = dimensionResource(R.dimen.list_item_height)
                    )
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_medium)
                    )
            )
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
            Image(
                painter = painterResource(R.drawable.list_master_bg),
                contentScale = ContentScale.Crop,
                alignment = cropAlignment,
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
                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(
                            width = dimensionResource(R.dimen.list_item_internal_text_width),
                            height = dimensionResource(R.dimen.list_item_internal_text_height)
                        )
                        .clip(ListItemInternalText)
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    Text(
                        text = getTranslatedCategoryName(category),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(dimensionResource(R.dimen.list_item_next_arrow_box))
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    IconButton (
                        onClick = { onCategorySelected(category) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardDoubleArrowRight,
                            contentDescription = stringResource(R.string.category_arrow_content_desc, category)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getTranslatedCategoryName(category: LocationCategory): String {
    return when (category) {
        LocationCategory.RESTAURANTS -> stringResource(R.string.restaurant_header)
        LocationCategory.CAFES -> stringResource(R.string.cafe_header)
        LocationCategory.PARKS -> stringResource(R.string.park_header)
        LocationCategory.TEMPLES -> stringResource(R.string.temple_header)
        LocationCategory.PRINTERS -> stringResource(R.string.mycelium_printer_header)
        LocationCategory.UNKNOWN -> stringResource(R.string.error_location)
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

