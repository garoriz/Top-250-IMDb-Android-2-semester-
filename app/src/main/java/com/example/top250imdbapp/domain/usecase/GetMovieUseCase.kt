package com.example.top250imdbapp.domain.usecase

import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.repo.MovieRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    operator fun invoke(id: String): Single<Movie> = movieRepository.getMovie(id)
        .subscribeOn(Schedulers.io())
}
