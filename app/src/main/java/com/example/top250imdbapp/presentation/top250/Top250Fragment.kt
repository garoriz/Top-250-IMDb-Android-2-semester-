package com.example.top250imdbapp.presentation.top250

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.top250imdbapp.R
import com.example.top250imdbapp.databinding.FragmentTop250Binding
import com.example.top250imdbapp.domain.entity.MovieInList
import com.example.top250imdbapp.presentation.movieDetails.MovieDetailsFragment
import com.example.top250imdbapp.presentation.top250.adapter.MovieListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

private const val ARG_NAME = "id"

@AndroidEntryPoint
class Top250Fragment : MvpAppCompatFragment(R.layout.fragment_top250), Top250View {
    @Inject
    @InjectPresenter
    lateinit var presenter: Top250Presenter

    @ProvidePresenter
    fun providePresenter(): Top250Presenter = presenter

    private var binding: FragmentTop250Binding? = null
    private var movieListAdapter: MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTop250Binding.bind(view)

        presenter.onGetTop250()
    }

    override fun showTop250(list: MutableList<MovieInList>) {
        movieListAdapter = MovieListAdapter { clickedMovie ->
            getMovieDetails(clickedMovie)
            movieListAdapter?.submitList(list)
        }

        binding?.movies?.run {
            adapter = movieListAdapter
        }

        movieListAdapter?.submitList(list)
    }

    private fun getMovieDetails(clickedMovie: String) {
        val bundle = Bundle()
        bundle.putString(ARG_NAME, clickedMovie)
        val movieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, movieDetailsFragment)
            ?.addToBackStack("top250")
            ?.commit()
    }

    override fun showError() {
        binding?.let {
            Snackbar.make(
                it.root,
                getString(R.string.error),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun showLoading() {
        binding?.progress?.isVisible = true
    }

    override fun hideLoading() {
        binding?.progress?.isVisible = false
    }
}
