package com.abdelmageed.marveltask.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto

@Composable
fun InnerCharacterDetailsList(list: List<CharacterDetailsListDto?>, onItemClick: (String?) -> Unit) {

    LazyRow {
        items(list) { item ->
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(140.dp)
                    .padding(start = 10.dp)
                    .clickable {
                        onItemClick(item?.resourceUrl)
                    }
            ) {
                AsyncImage(
                    model = item?.imageUrl?.replace("http", "https"),
                    contentDescription = "character",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    item?.name ?: "",
                    modifier = Modifier.padding(10.dp),
                    letterSpacing = (-0.5).sp,
                    fontFamily = FontFamily.Monospace
                )
            }

        }

    }
}