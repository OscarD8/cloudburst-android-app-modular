package com.example.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.R

@Composable
internal fun ExploreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    iconSize: Dp = 28.dp
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outline),
        elevation = ButtonDefaults.buttonElevation(dimensionResource(R.dimen.button_standard_elevation)),
        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                painter = painterResource(R.drawable.atr_icon),
                contentDescription = stringResource(R.string.explore_button),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = stringResource(R.string.explore_button),
                style = textStyle,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.button_text_start_padding),
                    bottom = dimensionResource(R.dimen.button_text_bottom_padding)
                )
            )
        }
    }
}