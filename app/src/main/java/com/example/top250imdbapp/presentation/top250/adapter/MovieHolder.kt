package com.example.top250imdbapp.presentation.top250.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.top250imdbapp.databinding.ItemMovieBinding
import com.example.top250imdbapp.domain.entity.MovieInList

class MovieHolder(
    private val binding: ItemMovieBinding,
    private val action: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var movie: MovieInList? = null

    init {
        itemView.setOnClickListener {
            movie?.id?.also(action)
        }
    }

    fun bind(item: MovieInList) {
        this.movie = item
        with(binding) {
            tvMovieName.text = item.fullTitle
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (String) -> Unit
        ) = MovieHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}
