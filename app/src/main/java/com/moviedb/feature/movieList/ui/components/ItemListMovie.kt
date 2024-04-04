package com.moviedb.feature.movieList.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviedb.R

@Preview(showBackground = true)
@Composable
fun ItemListMovie(
    modifier: Modifier = Modifier
) {
    Row {
        val image: Painter = painterResource(id = R.drawable.ic_broken_image)
        Image(
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(90.dp, 135.dp)

        )
        Column(modifier.) {
            Text(text = "18-02-24")
            Text(text = "Titulo Filme")
            Text(text = "Subtitulo filme")
        }
    }
}