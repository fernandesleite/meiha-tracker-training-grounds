package com.moviedb.feature.movieList.ui.fragment

import android.app.Activity
import android.os.Bundle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarLayout.appBar.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        NavigationUI.setupWithNavController(
            binding.toolbarLayout.toolbar,
            NavHostFragment.findNavController(requireParentFragment())
        )
        addPagination()

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
                        mAdapter.removeItems()
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