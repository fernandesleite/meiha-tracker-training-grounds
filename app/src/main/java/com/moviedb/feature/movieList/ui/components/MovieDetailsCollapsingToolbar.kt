package com.moviedb.feature.movieList.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun MovieDetailsCollapsingToolbar(modifier: Modifier = Modifier, imageUrl: String = "") {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(250.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            MeihaText(text = "castName", fontWeight = FontWeight.Bold)
            MeihaText(text = "castCharacter")
            Row(modifier = modifier) {
                MeihaText(text = "test1", modifier.padding(end = 16.dp))
                MeihaText(text = "test1", modifier.padding(end = 16.dp))
                MeihaText(text = "test1")
            }
        }
       MovieCover(modifier = modifier, imageUrl = imageUrl, contentDescription = "castName")

    }

}

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ...
}