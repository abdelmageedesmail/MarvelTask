package com.abdelmageed.marveltask.ui.details

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.ui.theme.SectionsDetailsBackground
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionsListDetails(
    navController: NavHostController,
    innerPadding: PaddingValues,
    resourceUri: String?,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val animatedScale = remember { Animatable(1f) }


    val state = rememberUpdatedState(viewModel.sectionDetailsState)
    var sectionDetailsResponse by remember { mutableStateOf<List<SectionImageDetailsDto?>>(emptyList()) }
    val context = LocalContext.current

    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        resourceUri?.let { viewModel.getSectionImages(it) }
        while (true) {
            animatedScale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )
            animatedScale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )
        }
    }

    LaunchedEffect(state) {
        viewModel.sectionDetailsState.collect {
            when (it) {
                is SectionDetailsState.SuccessGetDetails -> {
                    sectionDetailsResponse = it.response
                    loading = false
                }

                is SectionDetailsState.ErrorResponse -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    loading = false
                }

                is SectionDetailsState.Loading -> loading = it.isLoading
                is SectionDetailsState.Init -> Unit
                else -> Unit
            }
        }
    }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp


    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(SectionsDetailsBackground)
    ) {

        if (sectionDetailsResponse.isNotEmpty()) {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { sectionDetailsResponse.first()?.images?.size ?: 0 },
                modifier = Modifier.fillMaxSize(),
                preferredItemWidth = screenWidth.dp,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { itemIndex ->
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        sectionDetailsResponse.first()?.images?.get(itemIndex)
                            ?.replace("http", "https"),
                        modifier = Modifier
                            .fillMaxHeight(.6f)
                            .fillMaxWidth()
                            .maskClip(MaterialTheme.shapes.extraLarge)
                            .graphicsLayer(
                                scaleX = animatedScale.value,
                                scaleY = animatedScale.value
                            ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Text(
                        sectionDetailsResponse.first()?.name ?: "",
                        fontFamily = FontFamily.Monospace,
                        maxLines = 2,
                        color = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally)

                    )

                    Text(
                        "${itemIndex.plus(1)}/${sectionDetailsResponse.first()?.images?.size ?: 0}",
                        color = Color.White
                    )
                }

            }
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigateUp()
                    }
            )
        }
    }
}