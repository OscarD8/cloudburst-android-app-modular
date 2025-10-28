package com.example.ui.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.R
import com.example.ui.locations.LocationUiModel

@Composable
internal fun RatingRow(
    location: LocationUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Text(
            text = stringResource(R.string.expanded_item_rating),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
        )
        repeat(5) {
            Icon(
                imageVector = if (it < location.rating) Icons.Filled.Brightness7 else Icons.Default.Brightness5,
                contentDescription = stringResource(
                    R.string.list_location_rating_desc,
                    location.rating
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(dimensionResource(R.dimen.list_item_rating_icon_size))
            )
        }
    }
}