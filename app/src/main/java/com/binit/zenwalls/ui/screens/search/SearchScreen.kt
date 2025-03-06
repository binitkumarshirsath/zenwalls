package com.binit.zenwalls.ui.screens.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.binit.zenwalls.ui.screens.search.components.SearchBar
import com.binit.zenwalls.ui.util.searchKeywords
import org.koin.androidx.compose.koinViewModel

private  const val  TAG = "SearchScreen"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navcontroller: NavHostController,
    snackbarHostState: SnackbarHostState,
    searchScreenViewModel: SearchScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val query by searchScreenViewModel.query.collectAsState()

    val images = searchScreenViewModel.images.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

   Log.d(TAG,"images: ${images.itemCount}")

    Column {
        SearchBar(
            searchScreenViewModel,
            focusRequester = focusRequester,
            query = query ,
            onQueryChange = {
               searchScreenViewModel.setSearchQuery(it)
            },
            onQueryClear = { searchScreenViewModel.setSearchQuery("")},
            navHostController = navcontroller,
            modifier = modifier
        )
        Spacer(Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()
        ) {
            searchKeywords.map { searchKeyword ->
                Box(
                    modifier = Modifier.padding(end = 6.dp, top = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(50))
                            .background(
                                Color.Gray.copy(alpha = 0.1f)
                            )

                    ) {
                        Text(
                            text = searchKeyword,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

    }
}