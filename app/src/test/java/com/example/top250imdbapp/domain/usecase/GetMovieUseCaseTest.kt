package com.example.top250imdbapp.domain.usecase

import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.repo.MovieRepository
import com.example.top250imdbapp.utils.MainCoroutineRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetMovieUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    lateinit var useCase: GetMovieUseCase

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    @BeforeEach
    fun setUp() {
        useCase = GetMovieUseCase(
            movieRepository = repository,
            dispatcher = coroutineRule.testDispatcher
        )
    }

    @Test
    @DisplayName("Then request movie by id, we awaiting Movie")
    fun invoke() {
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

        every {
            repository.getMovie(expectedId)
        } returns Single.just(mockMovie)

        val result = useCase(expectedId)

        assertEquals(expectedFullTitle, result.blockingGet().fullTitle)
        assertEquals(expectedImage, result.blockingGet().image)
        assertEquals(expectedRuntimeStr, result.blockingGet().runtimeStr)
        assertEquals(expectedPlotLocal, result.blockingGet().plotLocal)
        assertEquals(expectedDirectors, result.blockingGet().directors)
    }

    @Test
    @DisplayName("Then request movie, we awaiting error")
    fun invokeError() {
        val expectedId = "id"
        val mockError = mockk<Throwable>()

        every {
            repository.getMovie(expectedId)
        } returns Single.error(mockError)

        val result = useCase(expectedId)

        assertEquals(mockError, result.blockingGet())
    }
}
