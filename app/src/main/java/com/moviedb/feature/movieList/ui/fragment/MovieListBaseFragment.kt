package com.moviedb.feature.movieList.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moviedb.R
import com.moviedb.databinding.FragmentMovieListBaseBinding
import com.moviedb.feature.movieDetails.ui.fragment.MovieDetailsFragment
import com.moviedb.feature.movieList.ui.adapter.MovieListAdapter
import com.moviedb.feature.movieList.ui.components.ItemListMovie
import com.moviedb.feature.movieList.viewModel.MovieListViewModel
import com.moviedb.persistence.model.Movie
import com.moviedb.util.KeyboardBehaviour
import com.moviedb.util.WatchedState
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MovieListBaseFragment : Fragment() {

    val movieListViewModel by viewModel<MovieListViewModel>()
    lateinit var binding: FragmentMovieListBaseBinding
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeView(inflater)
        initializeObservers()
        return binding.root
    }

    private fun initializeView(inflater: LayoutInflater) {
        binding = FragmentMovieListBaseBinding.inflate(inflater)

        val activity = requireNotNull(this.activity)
        activity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE

        // TODO: Implement FAB inside the lazycolumn 
        binding.floatingActionButton.setOnClickListener {
            animateFloatingButtonToGone()
        }
    }

    private fun initializeObservers() {
        getMovieList().observe(viewLifecycleOwner) {
            setMovieListPresentation(it)
        }

        movieListViewModel.errorEvent.observe(viewLifecycleOwner) {
            setErrorPresentation(it)
        }
    }

    private fun setErrorPresentation(it: String?) {
        binding.errorScreen.visibility = View.VISIBLE
        binding.errorMessage.text = it
    }

    private fun setMovieListPresentation(movieList: List<Movie>) {
            binding.movieListComposeView.setContent {

                val listState = rememberLazyListState()

                val reachedBottom: Boolean by remember {
                    derivedStateOf {
                        calcPositionToLoadItemsCompose(listState)
                    }
                }

                LaunchedEffect(reachedBottom) {
                    if (reachedBottom) loadMoreItems()
                }

                LazyColumn(state = listState) {
                    calcPositionToLoadItemsCompose(listState)

                    items(movieList) { movie ->
                        movie.apply {
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
                                    onClick = { navigateToMovieDetails(movie, binding.movieListComposeView) }
                                )
                            }
                        }
                    }
                }
            }

        binding.progressBar.visibility = View.GONE
        isLoading = true
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

    private fun calcPositionToLoadItemsCompose(listState: LazyListState): Boolean {
        val visibleItemCount = listState.layoutInfo.visibleItemsInfo.size
        val totalItemCount = listState.layoutInfo.totalItemsCount
        val firstVisible = listState.firstVisibleItemIndex
        return firstVisible + visibleItemCount >= totalItemCount - 4
    }


    fun animateFloatingButtonToVisible() {
        binding.floatingActionButton.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(400).setListener(null)
        }
    }

    fun animateFloatingButtonToGone() {
        binding.floatingActionButton.animate().alpha(0f).setDuration(400)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.floatingActionButton.visibility = View.GONE
                }
            })
    }

    fun loadMoreItems() {
        binding.progressBar.visibility = View.VISIBLE
        movieListViewModel.nextPage()
        isLoading = false
    }

    abstract fun getMovieList(): LiveData<List<Movie>>
}