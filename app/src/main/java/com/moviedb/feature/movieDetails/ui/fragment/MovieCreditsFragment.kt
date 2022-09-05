package com.moviedb.feature.movieDetails.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moviedb.databinding.FragmentMovieCreditsBinding
import com.moviedb.feature.movieDetails.ui.adapter.MovieCreditsAdapter
import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel

class MovieCreditsFragment(val viewModel: MovieDetailsViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = MovieCreditsAdapter()
        val binding = FragmentMovieCreditsBinding.inflate(inflater)
        binding.creditsList.adapter = adapter
        viewModel.credits.observe(viewLifecycleOwner) { credits ->
            adapter.submitList(credits.cast)
            binding.progressBar.visibility = View.GONE
        }
        return binding.root
    }
}
