package com.example.notes_app.uis.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.notes_app.R
import com.example.notes_app.data.API
import com.example.notes_app.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetail : AppCompatActivity() {

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com"
        private const val API_KEY = "1ddee4bf"
        private const val API_LOG_TAG = "MOVE_DETAIL"
        private const val IMDB_ID = "imdbID"
        private const val MESSAGE_ON_FAILURE = "onFailure "
    }

    private lateinit var retrofitBuilder: API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        retrofitBuilder()
        getData()
    }

    private fun retrofitBuilder() {
        retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL
            ).build().create(API::class.java)
    }

    private fun getData() {
        val intent = intent
        val search = intent.getStringExtra(IMDB_ID)
        if (search != null && search.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {

                val retrofitData = retrofitBuilder.getMovieDetail(search, API_KEY)

                retrofitData.enqueue(object : Callback<MovieDetails?> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<MovieDetails?>,
                        response: Response<MovieDetails?>
                    ) {
                        val result = response.body()

                        if (result != null && result.Response.equals("true", ignoreCase = true)) {

                            val movieGenre: TextView = findViewById(R.id.Genre)
                            val movieActors: TextView = findViewById(R.id.Actors)
                            val movieDirector: TextView = findViewById(R.id.Director)
                            val movieReleased: TextView = findViewById(R.id.Released)
                            val movieRating: TextView = findViewById(R.id.imdbRating)
                            val movieCountry: TextView = findViewById(R.id.Country)
                            val moviePlot: TextView = findViewById(R.id.plot)
                            val movieRuntime: TextView = findViewById(R.id.runtime)
                            val movieAwards: TextView = findViewById(R.id.awards)
                            val moviePoster: ImageView = findViewById(R.id.moviePoster)
                            val movieTitle: TextView = findViewById(R.id.movieTitle)

                            movieTitle.text = result.Title
                            movieGenre.text = "Genre: " + result.Genre
                            movieActors.text = "Actors: " + result.Actors
                            movieDirector.text = "Director: " + result.Director
                            movieReleased.text = "Release: " + result.Released
                            movieRating.text = "IMDB Rating: " + result.imdbRating
                            movieCountry.text = "Country: " + result.Country
                            moviePlot.text = "Plot: " + result.Plot
                            movieRuntime.text = "Runtime: " + result.Runtime
                            movieAwards.text = "Awards: " + result.Awards

                            Glide.with(this@MovieDetail).load(result.Poster).diskCacheStrategy(
                                DiskCacheStrategy.ALL
                            ).into(moviePoster)
                        }
                    }

                    override fun onFailure(call: Call<MovieDetails?>, t: Throwable) {
                        Log.d(API_LOG_TAG, MESSAGE_ON_FAILURE + t.message)

                    }
                })
            }
        }

    }
}