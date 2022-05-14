package com.example.top250imdbapp.presentation.movieDetails

import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.usecase.GetMovieUseCase
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
class MovieDetailsPresenterTest {

    @MockK
    lateinit var getMovieUseCase: GetMovieUseCase

    @MockK(relaxUnitFun = true)
    lateinit var viewState: `MovieDetailsView$$State`

    private lateinit var presenter: MovieDetailsPresenter

    @BeforeEach
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = spyk(
            MovieDetailsPresenter(getMovieUseCase = getMovieUseCase)
        )
        presenter.setViewState(viewState)
    }

    @Test
    fun `When select movie in list, we will expect success and show movie details`() {
        val expectedId = "id"
        val expectedFullTitle = "Avatar"
        val expectedImage = "imageUrl"
        val expectedRuntimeStr = "2h"
        val expectedPlotLocal = "Хороший фильм"
        val expectedDirectors = "Me"
        val mockMovie = mockk<Movie> {
            every { fullTitle } returns expectedFullTitle
            every { image } returns expectedImage
            every { runtimeStr } returns expectedRuntimeStr
            every { plotLocal } returns expectedPlotLocal
            every { directors } returns expectedDirectors
        }

        every { getMovieUseCase.invoke(expectedId) } returns Single.just(mockMovie)

        presenter.onGetMovieDetails(expectedId)

        verifyOrder {
            viewState.showLoading()
            viewState.hideLoading()
        }
        verify {
            viewState.showMovieDetails(mockMovie)
        }
    }

    @Test
    fun `When select movie in list, we will expect error`() {
        val expectedId = "id"
        val mockError = mockk<Throwable>()
        every { getMovieUseCase.invoke(expectedId) } returns Single.error(mockError)

        presenter.onGetMovieDetails(expectedId)

        verifyOrder {
            viewState.showLoading()
            viewState.hideLoading()
        }
        verify {
            viewState.showError()
        }
    }
}
