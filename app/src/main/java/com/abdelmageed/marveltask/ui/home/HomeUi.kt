package com.abdelmageed.marveltask.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.abdelmageed.marveltask.R
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.ui.NavigationItem
import com.abdelmageed.marveltask.ui.common.ToolBarUi
import com.abdelmageed.marveltask.ui.common.TripleOrbitLoadingAnimationPreview
import com.abdelmageed.marveltask.ui.theme.EndColor

@Composable
fun HomeUi(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characters = viewModel.state.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.getAllCharacters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        ToolBarUi {
            navController.navigate(NavigationItem.Search.route)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CharacterList(characters, navController, viewModel)
            if (viewModel.showLoading()) {
                TripleOrbitLoadingAnimationPreview(viewModel.showLoading())
            }
        }

    }
}

@Composable
fun CharacterList(
    results: LazyPagingItems<ResultsItem>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val gradientColors = listOf(Color.Transparent, Color.Transparent, EndColor)
    val context = LocalContext.current
    LazyColumn {
        items(
            count = results.itemCount,
            key = results.itemKey { result -> result.id ?: 0 },
            contentType = results.itemContentType { "results" }
        ) { index ->
            val character = results[index]
            viewModel.setLoading(false)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(gradientColors))
                    .clickable {
                        navController.navigate("${NavigationItem.CharacterDetails.route}/${character?.id}")
                    }

            ) {

                AsyncImage(
                    "${character?.thumbnail?.path}.${character?.thumbnail?.extension}".replace(
                        "http",
                        "https"
                    ),
                    contentDescription = "character",
                    contentScale = ContentScale.FillBounds,
                    onError = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                )


                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Brush.verticalGradient(gradientColors))
                )

                ElevatedButton(
                    onClick = {

                    }, modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Text(text = "${character?.name}", fontFamily = FontFamily.Monospace)
                }
            }
        }
        results.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    Toast.makeText(context, "error in loading data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}