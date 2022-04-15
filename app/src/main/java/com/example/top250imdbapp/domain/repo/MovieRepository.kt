package com.example.top250imdbapp.domain.repo

import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.entity.MovieInList
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun getTop250MoviesIMDb(): Single<MutableList<MovieInList>>

    fun getMovie(id: String): Single<Movie>
}
