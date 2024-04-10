package com.moviedb.feature.movieList.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ItemListCredits(
    modifier: Modifier = Modifier,
    coverUrl: String = "",
    castName: String = "Nome ator",
    castCharacter: String = "Nome Personagem",
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(135.dp)
            .fillMaxHeight()
    ) {
        MovieCover(modifier = modifier, imageUrl = coverUrl, contentDescription = castName)
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            MeihaText(text = castName, fontWeight = FontWeight.Bold)
            MeihaText(text = castCharacter)
        }
    }
}