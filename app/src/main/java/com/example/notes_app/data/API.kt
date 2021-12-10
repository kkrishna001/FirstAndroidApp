package com.example.notes_app.data

import com.example.notes_app.model.MovieDetails
import com.example.notes_app.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    companion object {
        private const val API_KEY = "apikey"
        private const val API_VALUE1 = "s"
        private const val API_VALUE2 = "i"
    }

    @GET("/")
    fun getMovies(
        @Query(API_VALUE1) s: String,
        @Query(API_KEY) apikey: String
    ): Call<MoviesResponse>

    @GET("/")
    fun getMovieDetail(
        @Query(API_VALUE2) i: String,
        @Query(API_KEY) apikey: String
    ): Call<MovieDetails>

}

