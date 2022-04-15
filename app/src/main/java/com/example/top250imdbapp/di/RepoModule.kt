package com.example.top250imdbapp.di

import com.example.top250imdbapp.data.MovieRepositoryImpl
import com.example.top250imdbapp.domain.repo.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    fun moviewRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}
