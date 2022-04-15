package com.example.top250imdbapp.data

import com.example.top250imdbapp.data.api.Api
import com.example.top250imdbapp.data.api.mapper.MovieMapper
import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.entity.MovieInList
import com.example.top250imdbapp.domain.repo.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: Api,
    private val movieMapper: MovieMapper,
) : MovieRepository {
    override fun getTop250MoviesIMDb(): Single<MutableList<MovieInList>> = api.getTop250MoviesIMDb()
        .map {
            movieMapper.map(it)
        }


    override fun getMovie(id: String): Single<Movie> = api.getMovie(id)
        .map {
            movieMapper.map(it)
        }
}
