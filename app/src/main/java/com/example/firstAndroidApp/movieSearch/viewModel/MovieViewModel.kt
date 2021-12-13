package com.example.firstAndroidApp.movieSearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstAndroidApp.movieSearch.data.MovieRepository
import com.example.firstAndroidApp.movieSearch.model.Movie
import com.example.firstAndroidApp.movieSearch.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movie: LiveData<List<Movie>>
        get() = movieRepository.movies

    val movieDetails: LiveData<MovieDetails>
        get() = movieRepository.movieDetail

    fun getMovies(movieName: String) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.getMovies(movieName)
    }

    fun getMovieDetails(movieName: String) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.getMovieDetail(movieName)
    }

}