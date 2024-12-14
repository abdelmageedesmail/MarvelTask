package com.abdelmageed.marveltask.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdelmageed.marveltask.R
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.UrlDto
import com.abdelmageed.marveltask.extensions.openLinkUrl
import com.abdelmageed.marveltask.ui.NavigationItem
import com.abdelmageed.marveltask.ui.common.CollapsingToolbar
import com.abdelmageed.marveltask.ui.common.TripleOrbitLoadingAnimationPreview
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailsUi(
    id: Int?,
    navController: NavController,
    innerPadding: PaddingValues,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val state by rememberUpdatedState(detailsViewModel.state)
    var details by remember { mutableStateOf(MarvelCharactersDto()) }
    var comics by remember { mutableStateOf(listOf<CharacterDetailsListDto?>()) }
    var series by remember { mutableStateOf(listOf<CharacterDetailsListDto?>()) }
    var events by remember { mutableStateOf(listOf<CharacterDetailsListDto?>()) }
    var stories by remember { mutableStateOf(listOf<CharacterDetailsListDto?>()) }
    var loading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        detailsViewModel.getVarientInnerList(id ?: 0)
    }
    LaunchedEffect(state) {
        detailsViewModel.state.collect {
            when (it) {
                is DetailsState.Init -> Unit
                is DetailsState.ErrorResponse -> {
                    loading = false
                }

                is DetailsState.Loading -> loading = it.isLoading
                is DetailsState.SuccessGetDetails -> details = it.response
                is DetailsState.SuccessGetComics -> {
                    comics = it.response
                    loading = false
                }

                is DetailsState.SuccessGetSeries -> series = it.response
                is DetailsState.SuccessGetEvents -> events = it.response
                is DetailsState.SuccessGetStories -> stories = it.response
                else -> Unit
            }
        }
    }

    val scrollState = rememberScrollState()
    val toolbarHeight = 300.dp
    val minToolbarHeight = 56.dp
    var offset by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(scrollState.value) {
        offset = scrollState.value.toFloat()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
            CollapsingToolbar(
                offset = offset,
                toolbarHeight = toolbarHeight,
                minToolbarHeight = minToolbarHeight,
                url = details.imageUrl?.replace("http", "https") ?: ""
            ) {
                navController.navigateUp()
            }

            if (!details.title.isNullOrEmpty()) {
                TitleText("Name")
                TitleDescription(details.title ?: "")
            }
            if (!details.description.isNullOrEmpty()) {
                TitleText("Description")
                TitleDescription(details.description ?: "")
            }

            if (comics.isNotEmpty()) {
                TitleText("Comics")
                InnerCharacterDetailsList(comics) {
                    navigateTo(navController, it ?: "")
                }
            }
            if (series.isNotEmpty()) {
                TitleText("Series")
                InnerCharacterDetailsList(series) {
                    navigateTo(navController, it ?: "")
                }
            }
            if (stories.isNotEmpty()) {
                TitleText("Stories")
                InnerCharacterDetailsList(stories) {
                    navigateTo(navController, it ?: "")
                }
            }
            if (events.isNotEmpty()) {
                TitleText("Events")
                InnerCharacterDetailsList(events) {
                    navigateTo(navController, it ?: "")
                }
            }
            if (!details.urls.isNullOrEmpty()) {
                TitleText("Related Links")
                RelatedLinks(details.urls ?: emptyList())
            }
        }

        TripleOrbitLoadingAnimationPreview(loading)
    }
}

fun navigateTo(navController: NavController, resourceUri: String) {
    val encodedJson = URLEncoder.encode(resourceUri, StandardCharsets.UTF_8.toString())
    navController.navigate("${NavigationItem.SectionsDetails.route}/$encodedJson")
}

@Composable
fun TitleText(title: String) {
    Text(
        title,
        color = Color.Red,
        modifier = Modifier.padding(10.dp),
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun TitleDescription(content: String) {
    Text(
        content, modifier = Modifier.padding(start = 10.dp),
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun RelatedLinks(urls: List<UrlDto>) {
    val context = LocalContext.current
    Column {
        urls.forEach { url ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable {
                        context.openLinkUrl(url.url ?: "")
                    }
            ) {
                Text(url.type ?: "", fontFamily = FontFamily.Monospace)
                Icon(
                    painter = painterResource(R.drawable.ic_forward),
                    contentDescription = "forward"
                )
            }
        }
    }
}