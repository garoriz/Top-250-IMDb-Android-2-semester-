package com.example.top250imdbapp.presentation.top250

import com.example.top250imdbapp.domain.entity.MovieInList
import com.example.top250imdbapp.domain.usecase.GetTop250MoviesIMDbUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class Top250PresenterTest {

    @MockK
    lateinit var getTop250UseCase: GetTop250MoviesIMDbUseCase

    @MockK(relaxUnitFun = true)
    lateinit var viewState: `Top250View$$State`

    private lateinit var presenter: Top250Presenter

    @BeforeEach
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = spyk(
            Top250Presenter(getTop250UseCase = getTop250UseCase)
        )
        presenter.setViewState(viewState)
    }

    @Test
    fun `When open list, we will expect success and show movie list`() {
        val mockMovieInList1 = mockk<MovieInList> {
            every { id } returns "id1"
            every { fullTitle } returns "Avatar"
        }
        val mockMovieInList2 = mockk<MovieInList> {
            every { id } returns "id2"
            every { fullTitle } returns "Batman"
        }
        val mockMovieInList3 = mockk<MovieInList> {
            every { id } returns "id3"
            every { fullTitle } returns "Avengers"
        }

        val mockMovieList = mutableListOf(mockMovieInList1, mockMovieInList2, mockMovieInList3)

        every { getTop250UseCase.invoke() } returns Single.just(mockMovieList)

        presenter.onGetTop250()

        verifyOrder {
            viewState.showLoading()
            viewState.hideLoading()
        }

        verify {
            viewState.showTop250(mockMovieList)
        }
    }

    @Test
    fun `When open list, we will expect error`() {
        val mockError = mockk<Throwable>()
        every { getTop250UseCase.invoke() } returns Single.error(mockError)

        presenter.onGetTop250()

        verifyOrder {
            viewState.showLoading()
            viewState.hideLoading()
        }
        verify {
            viewState.showError()
        }
    }
}

