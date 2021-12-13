package com.example.firstAndroidApp.movieSearch.data

import com.example.firstAndroidApp.movieSearch.model.MovieDetails
import com.example.firstAndroidApp.movieSearch.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    companion object {
        private const val API_KEY = "apikey"
        private const val API_VALUE1 = "s"
        private const val API_VALUE2 = "i"
    }

    @GET("/")
    suspend fun getMovies(
        @Query(API_VALUE1) s: String,
        @Query(API_KEY) apikey: String
    ): Response<MoviesResponse>

    @GET("/")
    suspend fun getMovieDetail(
        @Query(API_VALUE2) i: String,
        @Query(API_KEY) apikey: String
    ): Response<MovieDetails>

}

