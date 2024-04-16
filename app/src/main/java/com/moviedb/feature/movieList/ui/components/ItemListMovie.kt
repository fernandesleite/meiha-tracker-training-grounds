package com.moviedb.feature.movieList.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviedb.R
import com.moviedb.util.WatchedState

@Preview(showBackground = true)
@Composable
fun ItemListMovie(
    modifier: Modifier = Modifier,
    coverUrl: String = "",
    movieTitle: String = "titulo filme",
    movieSubtitle: String = "Subtitulo filme",
    releaseDate: String = "18-02-24",
    watchedState: WatchedState? = WatchedState.TO_WATCH,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
            .height(135.dp)
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier) {
            MovieCover(modifier = modifier, imageUrl = coverUrl, contentDescription = movieTitle)
            if (watchedState != null) {
                when (watchedState) {
                    WatchedState.WATCHED -> {
                        Image(
                            modifier = modifier.align(Alignment.BottomEnd),
                            painter = painterResource(id = R.drawable.ic_check_circle_18dp),
                            contentDescription = "watched"
                        )
                    }
                    WatchedState.TO_WATCH -> {
                        Image(
                            modifier = modifier.align(Alignment.BottomEnd),
                            painter = painterResource(id = R.drawable.ic_pending_18dp),
                            contentDescription = "to watch"
                        )
                    }
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MeihaText(text = releaseDate)
            MeihaText(text = movieTitle, fontWeight = FontWeight.Bold)
            MeihaText(text = movieSubtitle)
        }
    }
}