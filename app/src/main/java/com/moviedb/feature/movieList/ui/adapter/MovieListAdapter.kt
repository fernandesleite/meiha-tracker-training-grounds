package com.moviedb.feature.movieList.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.databinding.ItemListMovieBinding
import com.moviedb.feature.movieDetails.ui.fragment.MovieDetailsFragment
import com.moviedb.feature.movieList.ui.components.ItemListMovie
import com.moviedb.persistence.model.Movie
import com.moviedb.util.KeyboardBehaviour
import com.moviedb.util.WatchedState

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback) {
  class MovieViewHolder(private val composeView: ComposeView) :
    RecyclerView.ViewHolder(composeView) {
    fun bind(movie: Movie) {
      composeView.setContent {
        movie.apply {
          MaterialTheme {
            if (
              !poster_path.isNullOrEmpty() &&
              !title.isNullOrEmpty() &&
              !release_date.isNullOrEmpty() &&
              !genre_ids.isNullOrEmpty()
            ) {
              ItemListMovie(
                modifier = Modifier,
                coverUrl = poster_path,
                movieTitle = title,
                movieSubtitle = genre_ids.joinToString(),
                releaseDate = release_date,
                watchedState = setWatchedIconState(movie),
                onClick = { navigateToMovieDetails(movie, composeView) }
              )
            }
          }
        }
      }
    }

    private fun navigateToMovieDetails(movie: Movie, composeView: ComposeView) {
      val bundle = bundleOf(MovieDetailsFragment.MOVIE_ID to movie.id)
      composeView.findNavController().navigate(R.id.movieDetailsFragment, bundle)
      KeyboardBehaviour.hideKeyboard(composeView.context as Activity)
    }

    private fun setWatchedIconState(movie: Movie): WatchedState? {
      return when (movie.category) {
        1 -> WatchedState.WATCHED
        2 -> WatchedState.TO_WATCH
        else -> null
      }
    }
  }

  val list = mutableListOf<Movie>()
  fun addItems(items: MutableList<Movie>) {
    if (!currentList.containsAll(items)) {
      list.addAll(items)
      submitList(list)
      notifyItemRangeChanged(currentList.lastIndex, itemCount)
    }
  }

  fun removeItems() {
    list.clear()
    submitList(list)
    notifyItemRangeChanged(currentList.lastIndex, itemCount)
  }

  companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
      return oldItem.id == newItem.id
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    return MovieViewHolder(
      ComposeView(parent.context)
    )
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = getItem(position)
    holder.bind(movie)

  }
}