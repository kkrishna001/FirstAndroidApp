package com.example.firstAndroidApp.movieSearch.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstAndroidApp.movieSearch.model.Movie
import com.example.firstAndroidApp.movieSearch.model.MovieDetails
import com.example.firstAndroidApp.movieSearch.ui.fragment.MovieSearchFragment
import com.example.firstAndroidApp.movieSearch.ui.view.MovieDetail


class MovieRepository(private val moviesAPI: MoviesAPI) {

    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    private val movieDetails = MutableLiveData<MovieDetails>()
    val movieDetail: LiveData<MovieDetails>
        get() = movieDetails

    suspend fun getMovies(movieName: String) {

        val retrofitData = moviesAPI.getMovies(movieName)

        if (retrofitData.isSuccessful) {
            if (retrofitData.body() != null && retrofitData.body()!!.Response.equals(
                    "true",
                    ignoreCase = true
                )
            ) {
                val movie: List<Movie> = retrofitData.body()!!.Search
                movieLiveData.postValue(movie)
            } else {
                Log.d(MovieSearchFragment.API_LOG_TAG, " onFailure data is null ")
            }
        }
    }

    suspend fun getMovieDetail(movieName: String) {

        val retrofitData = moviesAPI.getMovieDetail(movieName)
        if (retrofitData.body() != null && retrofitData.body()!!.Response.equals(
                "true",
                ignoreCase = true
            )
        ) {
            movieDetails.postValue(retrofitData.body())
        } else {
            Log.d(MovieDetail.API_LOG_TAG_DETAIL, "onFailure data is null")
        }

    }
}