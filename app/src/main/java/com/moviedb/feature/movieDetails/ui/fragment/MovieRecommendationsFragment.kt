package com.moviedb.feature.movieDetails.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.moviedb.databinding.FragmentMovieRecommendationsBinding
import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel
import com.moviedb.feature.movieList.ui.adapter.MovieListAdapter

class MovieRecommendationsFragment(val viewModel: MovieDetailsViewModel) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val binding = FragmentMovieRecommendationsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.movieList.adapter = MovieListAdapter()
        viewModel.recommendations.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
        })
        return binding.root
    }
}
