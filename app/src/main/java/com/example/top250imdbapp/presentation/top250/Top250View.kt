package com.example.top250imdbapp.presentation.top250

import com.example.top250imdbapp.domain.entity.MovieInList
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface Top250View : MvpView {

    fun showTop250(list: MutableList<MovieInList>)

    @Skip
    fun showError()

    fun showLoading()

    fun hideLoading()
}
