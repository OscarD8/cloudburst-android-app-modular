package com.example.ui.locations.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun LocationsListScreen(
    onLocationClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsListViewModel = hiltViewModel(),
    onClickToExpand: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn {
        items(uiState.items, key = {it.id}) { locationItem ->
            // LocationCard
            // onClick = { onLocationClick(locationItem.id) }
        }
    }
}