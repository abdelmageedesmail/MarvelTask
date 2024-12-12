package com.abdelmageed.marveltask.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.ui.common.ToolBarUi
import com.abdelmageed.marveltask.ui.theme.EndColor
import com.abdelmageed.marveltask.utils.RoundedPolygonShape

@Composable
fun HomeUi(innerPadding: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {

    val state by rememberUpdatedState(viewModel.state)
    var characters by remember { mutableStateOf(MarvelCharacterResponse()) }

    LaunchedEffect(Unit) {
        viewModel.getAllCharacters()
    }

    LaunchedEffect(state) {
        viewModel.state.collect {
            when (it) {
                is HomeState.Init -> Unit
                is HomeState.ErrorResponse -> Unit
                is HomeState.SuccessGetCharacters -> characters = it.response
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        ToolBarUi()
        CharacterList(characters.data?.results)
    }
}

@Composable
fun CharacterList(results: List<ResultsItem?>?) {
    if (results != null) {
        val gradientColors = listOf(Color.Transparent, Color.Transparent, EndColor)
        val hexagon = remember {
            RoundedPolygon(
                6,
                rounding = CornerRounding(0.2f)
            )
        }
        val clip = remember(hexagon) {
            RoundedPolygonShape(polygon = hexagon)
        }
        LazyColumn {
            items(results) { result ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(gradientColors))

                ) {
                    Log.e("ImageUrl", "${result?.thumbnail?.path}.${result?.thumbnail?.extension}")
                    AsyncImage(
                        "${result?.thumbnail?.path}.${result?.thumbnail?.extension}".replace(
                            "http",
                            "https"
                        ),
                        contentDescription = "character",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)

                    )


                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Brush.verticalGradient(gradientColors))
                    )

//                    Box(
//                        modifier = Modifier
//                            .clip(clip)
//                            .background(MaterialTheme.colorScheme.secondary)
//                            .size(150.dp)
//                    ) {
//                        Text(text = "${result?.name}")
//                    }

                    ElevatedButton(
                        onClick = {

                        }, modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        Text(text = "${result?.name}", fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}