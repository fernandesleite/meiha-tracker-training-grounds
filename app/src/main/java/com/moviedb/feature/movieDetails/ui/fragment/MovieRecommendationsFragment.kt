package com.moviedb.feature.movieDetails.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moviedb.databinding.FragmentMovieRecommendationsBinding
import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel
import com.moviedb.feature.movieList.ui.adapter.MovieListAdapter

class MovieRecommendationsFragment(val viewModel: MovieDetailsViewModel) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = MovieListAdapter()
        val binding = FragmentMovieRecommendationsBinding.inflate(inflater)
        binding.movieList.adapter = adapter
        viewModel.recommendations.observe(viewLifecycleOwner) { recommendations ->
            binding.progressBar.visibility = View.GONE
            adapter.submitList(recommendations)
        }
        return binding.root
    }
}
