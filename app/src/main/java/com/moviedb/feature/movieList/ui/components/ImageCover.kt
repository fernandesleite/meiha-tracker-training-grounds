package com.moviedb.feature.movieList.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.moviedb.R
import com.moviedb.util.Constants

@Composable
fun MovieCover(
    modifier: Modifier = Modifier,
    imageUrl: String? = "",
    contentDescription: String? = ""
) {
    val fullUrl = "${Constants.FULL_IMAGE_IRL}$imageUrl"
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(fullUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_broken_image),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(90.dp, 135.dp)
    )
}