package com.example.top250imdbapp.presentation.movieDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.load
import com.example.top250imdbapp.R
import com.example.top250imdbapp.databinding.FragmentMovieDetailsBinding
import com.example.top250imdbapp.domain.entity.Movie
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

private const val ARG_NAME = "id"

@AndroidEntryPoint
class MovieDetailsFragment :
    MvpAppCompatFragment(R.layout.fragment_movie_details),
    MovieDetailsView {
    @Inject
    @InjectPresenter
    lateinit var presenter: MovieDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): MovieDetailsPresenter = presenter

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailsBinding.bind(view)

        val id = arguments?.getString(ARG_NAME).toString()
        presenter.onGetMovieDetails(id)
    }

    override fun showMovieDetails(movie: Movie) {
        with(binding) {
            tvTitle.text = movie.fullTitle
            ivPoster.load(movie.image)
            tvRuntimeValue.text = movie.runtimeStr
            tvPlot.text = movie.plotLocal
            tvDirectors.text = movie.directors
        }
    }

    override fun showError() {
        Snackbar.make(
            binding.root,
            getString(R.string.error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showLoading() {
        binding.progress.isVisible = true
    }

    override fun hideLoading() {
        binding.progress.isVisible = false
    }

}
