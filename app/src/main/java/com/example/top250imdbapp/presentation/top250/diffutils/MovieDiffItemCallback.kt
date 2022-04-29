package com.example.top250imdbapp.presentation.top250.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.top250imdbapp.domain.entity.MovieInList

class MovieDiffItemCallback : DiffUtil.ItemCallback<MovieInList>() {
    override fun areItemsTheSame(
        oldItem: MovieInList,
        newItem: MovieInList
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieInList,
        newItem: MovieInList
    ): Boolean = oldItem == newItem
}
