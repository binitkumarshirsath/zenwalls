package com.binit.zenwalls.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.navigation.NavHostController
import com.binit.zenwalls.ui.screens.search.components.SearchBar

@Composable
fun SearchScreen(
    navcontroller: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    var query by remember { mutableStateOf("") }

    Column {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onQueryClear = {query = ""},
            navHostController = navcontroller,
            modifier = modifier
        )

    }
}