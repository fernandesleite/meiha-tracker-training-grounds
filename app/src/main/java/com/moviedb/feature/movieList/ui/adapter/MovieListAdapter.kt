package com.moviedb.feature.movieList.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.databinding.ItemListMovieBinding
import com.moviedb.feature.movieDetails.ui.fragment.MovieDetailsFragment
import com.moviedb.persistence.model.Movie
import com.moviedb.util.BindingUtils
import com.moviedb.util.KeyboardBehaviour

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback) {
    class MovieViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                imagePoster.contentDescription = movie.title
                movieTitle.text = movie.title
                voteScore.text = movie.vote_average.toString()
                genreList.text = movie.genre_ids?.joinToString()

                setWatchedIconVisibility(movie)
                navigateToMovieDetails(movie)

                BindingUtils.bindImage(imagePoster, movie.poster_path)
                BindingUtils.bindMovieDateYear(movieReleaseDate, movie.release_date, true)
            }
        }

        private fun navigateToMovieDetails(movie: Movie) {
            itemView.setOnClickListener { view ->
                val bundle = bundleOf(MovieDetailsFragment.MOVIE_ID to movie.id)
                view.findNavController().navigate(R.id.movieDetailsFragment, bundle)
                KeyboardBehaviour.hideKeyboard(view.context as Activity)
            }
        }

        private fun ItemListMovieBinding.setWatchedIconVisibility(movie: Movie) {
            when (movie.category) {
                1 -> watchImage.visibility = View.VISIBLE
                2 -> {
                    watchImage.visibility = View.VISIBLE
                    watchImage.setImageResource(R.drawable.ic_pending_18dp)
                }
                else -> {
                    watchImage.visibility = View.GONE
                }
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
            ItemListMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

    }
}