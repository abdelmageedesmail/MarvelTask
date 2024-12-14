package com.abdelmageed.marveltask.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import com.abdelmageed.marveltask.R
import com.abdelmageed.marveltask.ui.theme.SectionsDetailsBackground
import kotlin.math.max
import kotlin.math.min

@Composable
fun ToolBarUi(onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SectionsDetailsBackground),
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
                .align(Alignment.CenterEnd)
                .clickable {
                    onSearchClick()
                }, tint = Color.Red
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun CollapsingToolbar(
    offset: Float,
    toolbarHeight: Dp,
    minToolbarHeight: Dp,
    url: String,
    onBackClick: () -> Unit
) {
    val collapseFraction =
        min(1f, max(0f, offset / with(LocalDensity.current) { toolbarHeight.toPx() }))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(lerp(toolbarHeight, minToolbarHeight, collapseFraction))
    ) {
        AsyncImage(
            url,
            contentDescription = "marvelCharacter",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(toolbarHeight)
                .offset(y = -(offset / 2).dp)
                .alpha(1f - collapseFraction),
        )

        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}