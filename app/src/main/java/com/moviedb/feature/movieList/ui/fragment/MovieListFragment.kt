package com.moviedb.feature.movieList.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import com.moviedb.databinding.FragmentMovieDetailsBinding
import com.moviedb.databinding.FragmentMovieListBinding
import com.moviedb.feature.movieList.ui.adapter.MovieListPagerAdapter
import com.moviedb.feature.movieList.viewModel.MovieListViewModel
import com.moviedb.util.KeyboardBehaviour
class MovieListFragment : Fragment() {

    lateinit var viewModel: MovieListViewModel
    private lateinit var viewBinding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        KeyboardBehaviour.hideKeyboard(context as Activity)
        viewBinding = FragmentMovieListBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewBinding.viewpager
        val toolbar = viewBinding.toolbar
        val tabs = viewBinding.tabs

        viewPager.adapter = MovieListPagerAdapter(this)

        toolbar.title = getString(R.string.explore)

        toolbar.inflateMenu(R.menu.search_menu)

        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.search) {
                findNavController().navigate(R.id.searchMovieListFragment)
                true
            } else false
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.popular)
                    }
                    1 -> {
                        tab.text = getString(R.string.now_playing)
                    }
                    2 -> {
                        tab.text = getString(R.string.upcoming)
                    }
                    3 -> {
                        tab.text = getString(R.string.top_rated)
                    }
                }
            }.attach()
    }
}

