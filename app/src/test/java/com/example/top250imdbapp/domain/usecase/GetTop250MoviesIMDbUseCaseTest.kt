package com.example.top250imdbapp.domain.usecase

import com.example.top250imdbapp.domain.entity.MovieInList
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
class GetTop250MoviesIMDbUseCaseTest {

    @MockK
    lateinit var repository: MovieRepository

    lateinit var useCase: GetTop250MoviesIMDbUseCase

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    @BeforeEach
    fun setUp() {
        useCase = GetTop250MoviesIMDbUseCase(
            movieRepository = repository,
            dispatcher = coroutineRule.testDispatcher
        )
    }

    @Test
    @DisplayName("Then request movie list, we awaiting mutable list of MovieInList")
    fun invoke() {
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

        every {
            repository.getTop250MoviesIMDb()
        } returns Single.just(mockMovieList)

        val result = useCase()

        assertEquals(mockMovieInList1, result.blockingGet()[0])
        assertEquals(mockMovieInList2, result.blockingGet()[1])
        assertEquals(mockMovieInList3, result.blockingGet()[2])
    }

    @Test
    @DisplayName("Then request movie list, we awaiting error")
    fun invokeError() {
        val mockError = mockk<Throwable>()

        every {
            repository.getTop250MoviesIMDb()
        } returns Single.error(mockError)

        val result = useCase()

        assertEquals(mockError, result.blockingGet())
    }
}
