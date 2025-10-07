package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun FactScreen(
    modifier: Modifier = Modifier,
    viewModel: FactViewModel = hiltViewModel()
) {
    val fact by viewModel.fact
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = fact)
    }
}