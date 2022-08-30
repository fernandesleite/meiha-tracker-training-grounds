package com.moviedb.feature.movieList.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moviedb.feature.movieList.ui.fragment.NowPlayingMovieListFragment
import com.moviedb.feature.movieList.ui.fragment.PopularMovieListFragment
import com.moviedb.feature.movieList.ui.fragment.TopRatedMovieListFragment
import com.moviedb.feature.movieList.ui.fragment.UpcomingMovieListFragment


class MovieListPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMovieListFragment()
            1 -> NowPlayingMovieListFragment()
            2 -> UpcomingMovieListFragment()
            3 -> TopRatedMovieListFragment()
            else -> PopularMovieListFragment()
        }
    }
}