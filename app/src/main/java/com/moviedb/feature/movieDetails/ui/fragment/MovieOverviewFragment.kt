package com.moviedb.feature.movieDetails.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moviedb.databinding.FragmentMovieOverviewBinding
import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel
import com.moviedb.util.BindingUtils

class MovieOverviewFragment(var viewModel: MovieDetailsViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val binding = FragmentMovieOverviewBinding.inflate(inflater)
        viewModel.details.observe(viewLifecycleOwner) { details ->
            binding.synopsis.text = details.overview
            binding.status.text = details.status
            binding.original.text = details.original_title
            binding.originalLanguage.text = details.original_language
            BindingUtils.bindIntToCurrency(binding.budget, details.budget)
            BindingUtils.bindIntToCurrency(binding.budget, details.revenue)
            binding.spokenLanguages.text =
                details.spoken_languages.joinToString("\n\n") { language ->
                    language.name
                }
            binding.productionCompanies.text =
                details.production_companies.joinToString("\n\n") { companies ->
                    companies.name
                }
            binding.progressBar.visibility = View.GONE
            binding.overviewLayout.visibility = View.VISIBLE
        }
        return binding.root
    }
}
