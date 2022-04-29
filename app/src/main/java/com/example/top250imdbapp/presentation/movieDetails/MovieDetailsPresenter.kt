package com.example.top250imdbapp.presentation.movieDetails

import com.example.top250imdbapp.domain.usecase.GetMovieUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import moxy.MvpPresenter
import javax.inject.Inject

class MovieDetailsPresenter @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : MvpPresenter<MovieDetailsView>() {
    private val disposables = CompositeDisposable()

    fun onGetMovieDetails(id: String) {
        disposables += getMovieUseCase(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoading()
            }
            .doAfterTerminate {
                viewState.hideLoading()
            }
            .subscribeBy(onSuccess = {
                viewState.showMovieDetails(it)
            }, onError = {
                viewState.showError()
            })
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
