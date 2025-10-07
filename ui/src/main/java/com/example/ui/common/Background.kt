package com.example.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.ui.R


/**
 * A consistent background composable for the entire app.
 *
 * It displays a static background image that fills the screen. It intelligently applies
 * a semi-transparent dark overlay when the system's dark theme is active to ensure
 * content readability and improve aesthetics. ✨
 *
 * @param modifier A [Modifier] to be applied to the background container.
 */
@Composable
fun CloudburstBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.cloudburst_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (isSystemInDarkTheme()) {
            Surface (
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxSize()
            ) {}
        }
    }
}