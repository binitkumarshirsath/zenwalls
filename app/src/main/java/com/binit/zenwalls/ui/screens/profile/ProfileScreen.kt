package com.binit.zenwalls.ui.screens.profile

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    photographerProfileLink: String,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isPageLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Column(modifier.fillMaxSize()) {
        TopAppBar(
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
                    BackButton(onBackClick)
                }
            },

            )

        AndroidView(
            factory = {
                WebView(context).apply {
                    loadUrl(photographerProfileLink)
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isPageLoading = false
                        }
                    }

                }
            }
        )

    }
}