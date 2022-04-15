package com.example.top250imdbapp.data.api.mapper

import com.example.top250imdbapp.data.api.response.movie.MovieResponse
import com.example.top250imdbapp.data.api.response.top250moviesIMDb.Item
import com.example.top250imdbapp.data.api.response.top250moviesIMDb.Top250MoviesIMDbResponse
import com.example.top250imdbapp.domain.entity.Movie
import com.example.top250imdbapp.domain.entity.MovieInList

class MovieMapper {
    fun map(response: Top250MoviesIMDbResponse): MutableList<MovieInList> {
        return response.items.map(this::map) as MutableList<MovieInList>
    }

    private fun map(item: Item): MovieInList = MovieInList(
        id = item.id,
        fullTitle = item.fullTitle
    )

    fun map(response: MovieResponse): Movie = Movie(
        fullTitle = response.fullTitle,
        image = response.image,
        runtimeStr = response.runtimeStr,
        plotLocal = response.plotLocal,
        directors = response.directors
    )
}
