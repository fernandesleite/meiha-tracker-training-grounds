package com.moviedb.util

import com.moviedb.network.model.TMDbGenre
import com.moviedb.persistence.model.Genre

fun List<TMDbGenre>.toDatabase() : List<Genre>{
    return map {
        Genre(id = it.id,
            name = it.name
        )
    }
}