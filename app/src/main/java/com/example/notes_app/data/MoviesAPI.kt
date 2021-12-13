package com.example.notes_app.data

import com.example.notes_app.model.Movie
import com.example.notes_app.model.MovieDetails
import com.example.notes_app.model.MoviesResponse
import retrofit2.Call
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

