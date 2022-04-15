package com.example.top250imdbapp.data.api

import com.example.top250imdbapp.data.api.response.movie.MovieResponse
import com.example.top250imdbapp.data.api.response.top250moviesIMDb.Top250MoviesIMDbResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("Top250Movies/k_h6vz78i5")
    fun getTop250MoviesIMDb(): Single<Top250MoviesIMDbResponse>

    @GET("Title/k_h6vz78i5/{id}")
    fun getMovie(@Path("id") id: String): Single<MovieResponse>
}
