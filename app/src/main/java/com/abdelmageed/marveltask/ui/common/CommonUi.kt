package com.abdelmageed.marveltask.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdelmageed.marveltask.R

@Composable
fun ToolBarUi() {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(
                R.drawable.ic_marvel
            ),
            contentDescription = "marvel_logo",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "search",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterEnd), tint = Color.Red
        )
    }
}