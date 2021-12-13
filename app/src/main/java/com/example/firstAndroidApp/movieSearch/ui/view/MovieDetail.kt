package com.example.firstAndroidApp.movieSearch.ui.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.firstAndroidApp.movieSearch.data.MoviesAPI
import com.example.firstAndroidApp.databinding.ActivityMovieDetailBinding
import com.example.firstAndroidApp.util.GlideUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private var glideUtil = GlideUtil()

    private lateinit var retrofitBuilder: MoviesAPI

    private lateinit var viewBinding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        retrofitBuilder()
        getData()
    }

    private fun retrofitBuilder() {
        retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL
            ).build().create(MoviesAPI::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        val intent = intent
        val search = intent.getStringExtra(IMDB_ID)
        if (search != null && search.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {

                val retrofitData = retrofitBuilder.getMovieDetail(search, API_KEY)

                if (retrofitData.isSuccessful) {

                    if (retrofitData.body() != null && retrofitData.body()!!.Response.equals(
                            "true",
                            ignoreCase = true
                        )
                    ) {
                        val result = retrofitData.body()

                        viewBinding.movieTitle.text = result?.Title
                        viewBinding.Genre.text = "Genre: ${result?.Genre}"
                        viewBinding.Actors.text = "Actors: ${result?.Actors}"
                        viewBinding.Director.text = "Director: ${result?.Director}"
                        viewBinding.Released.text = "Release: ${result?.Released}"
                        viewBinding.imdbRating.text = "IMDB Rating: ${result?.imdbRating}"
                        viewBinding.Country.text = "Country: ${result?.Country}"
                        viewBinding.plot.text = "Plot: ${result?.Plot}"
                        viewBinding.runtime.text = "Runtime: ${result?.Runtime}"
                        viewBinding.awards.text = "Awards: ${result?.Awards}"


                        result?.Poster?.let {
                            glideUtil.setImage(
                                this@MovieDetail,
                                it, viewBinding.moviePoster
                            )
                        }


                    } else {
                        Log.d(API_LOG_TAG, MESSAGE_ON_FAILURE.plus(retrofitData.errorBody()))
                    }
                } else {
                    Log.d(API_LOG_TAG, MESSAGE_ON_FAILURE.plus(retrofitData.errorBody()))

                }

            }
        }

    }
}