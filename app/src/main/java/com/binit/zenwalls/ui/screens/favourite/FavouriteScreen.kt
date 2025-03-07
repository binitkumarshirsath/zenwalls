package com.binit.zenwalls.ui.screens.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Secondary
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navcontroller: NavHostController,
    snackbarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    favouriteScreenViewModel: FavouriteScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Row {
                        Spacer(Modifier.width(20.dp))
                        Text(
                            textAlign = TextAlign.Center,
                            text = buildAnnotatedString {
                                append("Zen")
                                addStyle(
                                    style = SpanStyle(
                                        color = Secondary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    start = 0,
                                    end = 3
                                )
                                append(" ")
                                append("Walls")
                                addStyle(
                                    style = SpanStyle(
                                        color = Primary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    start = 3,
                                    end = 9
                                )
                            }
                        )
                    }
                },
                navigationIcon = {
                    Row {
                        Spacer(Modifier.width(10.dp))
                        BackButton(
                            onBackClick = {
                                navcontroller.navigateUp()
                            }
                        )
                    }
                },
            )


        }
    }
}