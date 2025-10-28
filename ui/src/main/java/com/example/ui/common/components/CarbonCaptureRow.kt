package com.example.ui.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Beenhere
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.locations.LocationUiModel
import com.example.ui.R

@Composable
internal fun CarbonRatingRow(
    location: LocationUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.expanded_item_carbon_capture),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Icon(
            imageVector = if (location.isCarbonCapturing) Icons.Filled.Beenhere else Icons.Outlined.HeartBroken,
            contentDescription = stringResource(
                R.string.list_location_carbon_desc,
                location.isCarbonCapturing
            ),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(dimensionResource(R.dimen.list_item_carbon_item_size))
                .padding(start = dimensionResource(R.dimen.padding_small))
        )
    }
}