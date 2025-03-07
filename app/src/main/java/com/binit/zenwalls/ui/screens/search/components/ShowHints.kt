package com.binit.zenwalls.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.util.searchKeywords

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowHints(
    setQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                setQuery(searchKeyword)
                            }
                    )
                }
            }
        }
    }
}