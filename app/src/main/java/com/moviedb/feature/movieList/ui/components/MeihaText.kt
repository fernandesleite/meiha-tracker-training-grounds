package com.moviedb.feature.movieList.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.moviedb.R

@Composable
fun MeihaText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Normal
) {
    val poppinsFamily = FontFamily(
        Font(R.font.poppins, FontWeight.Normal)

    )
    Text(
        text = text,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}