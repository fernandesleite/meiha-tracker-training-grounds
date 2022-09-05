package com.moviedb.feature.movieList.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moviedb.R
import com.moviedb.databinding.FragmentMovieListBaseBinding
import com.moviedb.feature.movieList.ui.adapter.MovieListAdapter
import com.moviedb.feature.movieList.viewModel.MovieListViewModel
import com.moviedb.persistence.model.Movie
import com.moviedb.util.KeyboardBehaviour
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MovieListBaseFragment : Fragment() {

    val movieListViewModel by viewModel<MovieListViewModel>()
    lateinit var binding: FragmentMovieListBaseBinding
    lateinit var mAdapter: MovieListAdapter

    private var adapter = MovieListAdapter()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity)
        activity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
        binding = FragmentMovieListBaseBinding.inflate(inflater)
        binding.apply {
            movieList.adapter = adapter
        }
        mAdapter = binding.movieList.adapter as MovieListAdapter
        getMovieList().observe(viewLifecycleOwner) {
            it.map { movie ->
                movieListViewModel.getMovie(movie.id).observe(viewLifecycleOwner){ int ->
                    mAdapter.notifyDataSetChanged()
                    movie.category = int
                }
            }
            mAdapter.addItems(it.toMutableList())
            binding.progressBar.visibility = View.GONE
            isLoading = true
        }

        movieListViewModel.errorEvent.observe(viewLifecycleOwner) {
            binding.errorScreen.visibility = View.VISIBLE
            binding.errorMessage.text = it
        }

        return binding.root
    }

    fun addPagination() {
        val toTheTopButton = binding.floatingActionButton
        fun animateFloatingButtonToVisible() {
            toTheTopButton.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(400).setListener(null)
            }
        }

        fun animateFloatingButtonToGone() {
            toTheTopButton.animate().alpha(0f).setDuration(400)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        toTheTopButton.visibility = View.GONE
                    }
                })
        }

        toTheTopButton.setOnClickListener {
            binding.movieList.scrollToPosition(0)
            animateFloatingButtonToGone()
        }

        fun loadMoreItems() {
            movieListViewModel.nextPage()
            isLoading = false
        }

        fun calcPositionToLoadItems(recyclerView: RecyclerView): Boolean {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            return firstVisible + visibleItemCount >= totalItemCount
        }
        binding.movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) {
                    animateFloatingButtonToGone()
                } else if (toTheTopButton.visibility == View.GONE && dy > 0) {
                    KeyboardBehaviour.hideKeyboard(context as Activity)
                    animateFloatingButtonToVisible()
                }
                if (calcPositionToLoadItems(recyclerView) && isLoading) {
                    loadMoreItems()
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        adapter = binding.movieList.adapter as MovieListAdapter
    }

    abstract fun getMovieList(): LiveData<List<Movie>>
}