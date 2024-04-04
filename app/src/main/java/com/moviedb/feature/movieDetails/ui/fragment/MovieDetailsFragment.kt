package com.moviedb.feature.movieDetails.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import com.moviedb.databinding.FragmentMovieDetailsBinding
import com.moviedb.feature.movieDetails.ui.adapter.MovieDetailsPagerAdapter
import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel
import com.moviedb.network.model.TMDbMovieDetails
import com.moviedb.util.BindingUtils
import com.moviedb.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        val movieId = requireArguments().getInt(MOVIE_ID)
        movieDetailsViewModel.getMovieInfo(movieId)
        val toolbar = binding.toolbar
        setToolbar(toolbar)

        movieDetailsViewModel.getMovieCategory(movieId).observe(viewLifecycleOwner) { category ->
            setUpCategoryMenu(toolbar, category)
        }

        movieDetailsViewModel.details.observe(viewLifecycleOwner) { details ->
            setDetailsBindings(details)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewpager
        viewPager.adapter = MovieDetailsPagerAdapter(this, movieDetailsViewModel)

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.overview)
                }
                1 -> {
                    tab.text = getString(R.string.credits)
                }
                2 -> {
                    tab.text = getString(R.string.more_like_this)
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        val activity = requireNotNull(this.activity)
        activity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }

    private fun setUpCategoryMenu(toolbar: Toolbar, category: Int?) {
        toolbar.menu.clear()
        binding.apply {
            when (category) {
                1 -> {
                    watchImage.visibility = View.VISIBLE
                    watchImage.setImageResource(R.drawable.ic_check_circle_18dp)
                    watchText.text = getString(R.string.watched)
                    toolbar.inflateMenu(R.menu.watched_group_menu)
                }
                2 -> {
                    watchImage.visibility = View.VISIBLE
                    watchText.text = getString(R.string.to_watch)
                    watchImage.setImageResource(R.drawable.ic_pending_18dp)
                    toolbar.inflateMenu(R.menu.to_watch_group_menu)
                }
                else -> {
                    watchImage.visibility = View.GONE
                    watchText.text = ""
                    toolbar.inflateMenu(R.menu.unlabeled_group_menu)
                }
            }
        }
    }

    private fun setToolbar(toolbar: Toolbar) {
        NavigationUI.setupWithNavController(
            toolbar,
            NavHostFragment.findNavController(requireParentFragment())
        )
        setToolbarClickListener(toolbar)
    }

    private fun setToolbarClickListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener { item ->
            movieDetailsViewModel.apply {
                details.observe(viewLifecycleOwner) {
                    when (item.itemId) {
                        R.id.add_watched -> {
                            movieToCache(1, it.id)
                        }
                        R.id.add_to_watch -> {
                            movieToCache(2, it.id)
                        }
                        R.id.delete -> {
                            deleteMovie(it.id)
                        }
                    }
                }
            }
            true
        }
    }

    private fun setDetailsBindings(details: TMDbMovieDetails) {
        binding.apply {
            setDetails(details)
        }
    }

    private fun FragmentMovieDetailsBinding.setDetails(details: TMDbMovieDetails) {
        collapsingToolbar.title = details.title
        backdropImage.contentDescription = details.backdrop_path
        runtime.text = getString(R.string.runtimeFormat, details.runtime.toString())
        expandedTitle.text = details.title
        movieImage.contentDescription = details.poster_path
        BindingUtils.bindImage(movieImage, details.poster_path)
        BindingUtils.bindMovieDateYear(monthYearRelease, details.release_date, false)
        setBackdropImage(details)
        genreList.text = details.genres.joinToString { genre ->
            genre.name
        }
        voteScore.text = details.vote_average.toString()
    }

    private fun FragmentMovieDetailsBinding.setBackdropImage(details: TMDbMovieDetails) {
        val fullUri = "${Constants.FULL_IMAGE_IRL}${details.backdrop_path}"
        val imgUri = fullUri.toUri().buildUpon().scheme(Constants.SCHEME).build()
        context?.let { context ->
            Glide.with(context)
                .load(imgUri)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        detailsLayout.visibility = View.VISIBLE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        detailsLayout.visibility = View.VISIBLE
                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(8))
                .into(backdropImage)
        }
    }

    companion object {
        const val MOVIE_ID = "movieId"
    }
}
