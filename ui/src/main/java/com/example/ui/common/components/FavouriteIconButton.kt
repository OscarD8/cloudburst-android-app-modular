package com.example.ui.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ui.R
import com.example.ui.locations.LocationUiModel

@Composable
internal fun FavouriteIconButton(
    location: LocationUiModel,
    onClickToFavourite: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClickToFavourite
    ) {
        Icon(
            imageVector = if (!location.isFavourite) Icons.Outlined.BookmarkAdd else Icons.Default.BookmarkAdded,
            contentDescription = stringResource(R.string.expanded_item_favourite_icon_desc),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}