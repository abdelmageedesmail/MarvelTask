package com.abdelmageed.marveltask.ui.search

import android.graphics.ColorSpace
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.ui.NavigationItem
import com.abdelmageed.marveltask.ui.common.TripleOrbitLoadingAnimationPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUi(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val state by rememberUpdatedState(viewModel.state)
    var characters by remember { mutableStateOf(listOf<MarvelCharactersDto?>()) }
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        viewModel.state.collect {
            when (it) {
                is SearchState.Success -> {
                    characters = it.data
                    loading = false
                }

                is SearchState.Error -> {
                    loading = false
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is SearchState.Loading -> loading = it.isLoading
                is SearchState.Init -> Unit
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            , verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {
                    active = false
                    Log.e("SearchQuery", query)
                    viewModel.search(query)
                },
                active = active,
                onActiveChange = { active = it },

                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .weight(1f),

                placeholder = { Text("Search...") },

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingIcon = {
                    if (active)
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                ),
                tonalElevation = 0.dp,
            ) {
            }

            TextButton(onClick = {
                navController.navigateUp()
            }, modifier = Modifier.align(Alignment.Bottom).padding(bottom = 12.dp)) {
                Text(text = "Cancel", fontFamily = FontFamily.Monospace, color = Color.Red)
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyColumn {
                items(characters) { character ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${NavigationItem.CharacterDetails.route}/${character?.id}")
                        }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                character?.imageUrl?.replace("http", "https"),
                                contentDescription = "characterImage",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .width(70.dp)
                                    .height(70.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = character?.title ?: "",
                                modifier = Modifier.padding(start = 12.dp),
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            thickness = .5.dp,
                            color = Color.Gray
                        )
                    }
                }
            }
            TripleOrbitLoadingAnimationPreview(loading)
        }
    }
}