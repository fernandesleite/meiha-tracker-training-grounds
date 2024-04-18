package com.moviedb.feature.movieList.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.moviedb.R
import com.moviedb.persistence.model.Movie
import com.moviedb.util.KeyboardBehaviour

class SearchMovieListFragment : MovieListBaseFragment() {

    override fun getMovieList(): LiveData<List<Movie>> {
        return movieListViewModel.searchMovies
    }

    @SuppressLint("MissingPermission", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =  connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities.also {
            if (it != null) {
                val test = it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                Log.d("TAG", "onViewCreated: $test")
            } else {
                Log.d("TAG", "onViewCreated: false")
            }
        }

        binding.toolbarLayout.appBar.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        NavigationUI.setupWithNavController(
            binding.toolbarLayout.toolbar,
            NavHostFragment.findNavController(requireParentFragment())
        )

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.apply {
            toolbar.inflateMenu(R.menu.search_bar)
            val searchItem = menu.findItem(R.id.search_bar)

            val sv = searchItem.actionView as SearchView
            sv.apply {
                setIconifiedByDefault(false)
                requestFocus()
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        movieListViewModel.getSearchQuery(query ?: "")
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        movieListViewModel.resetPage()
                        movieListViewModel.getSearchQuery(newText ?: "")
                        return false
                    }

                })
            }
        }
        movieListViewModel.apply {
            page.observe(viewLifecycleOwner, Observer {
                if (it > 1) {
                    getSearchQuery(movieListViewModel.searchQuery)
                }
            })
            resetPage()
        }
    }

    override fun onResume() {
        super.onResume()
        KeyboardBehaviour.showKeyboard(context as Activity)
    }
}