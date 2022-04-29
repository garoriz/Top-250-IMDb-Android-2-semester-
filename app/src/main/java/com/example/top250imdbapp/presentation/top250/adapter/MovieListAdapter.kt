package com.example.top250imdbapp.presentation.top250.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.top250imdbapp.domain.entity.MovieInList
import com.example.top250imdbapp.presentation.top250.diffutils.MovieDiffItemCallback

class MovieListAdapter(
    private val action: (String) -> Unit,
) : ListAdapter<MovieInList, MovieHolder>(MovieDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder = MovieHolder.create(parent, action)

    override fun onBindViewHolder(holder: MovieHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<MovieInList>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
