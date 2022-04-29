package com.example.top250imdbapp.presentation.top250

import com.example.top250imdbapp.domain.usecase.GetTop250MoviesIMDbUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import moxy.MvpPresenter
import javax.inject.Inject

class Top250Presenter @Inject constructor(
    private val getTop250UseCase: GetTop250MoviesIMDbUseCase,
) : MvpPresenter<Top250View>() {
    private val disposables = CompositeDisposable()

    fun onGetTop250() {
        disposables += getTop250UseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoading()
            }
            .doAfterTerminate {
                viewState.hideLoading()
            }
            .subscribeBy(onSuccess = {
                viewState.showTop250(it)
            }, onError = {
                viewState.showError()
            })
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
