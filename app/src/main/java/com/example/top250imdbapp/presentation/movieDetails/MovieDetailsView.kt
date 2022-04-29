package com.example.top250imdbapp.presentation.movieDetails

import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.entity.MovieInList
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface MovieDetailsView : MvpView {
    fun showMovieDetails(movie: Movie)

    @Skip
    fun showError()

    fun showLoading()

    fun hideLoading()
}
