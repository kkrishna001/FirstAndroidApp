package com.example.firstAndroidApp.movieSearch.ui.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.firstAndroidApp.movieSearch.data.MoviesAPI
import com.example.firstAndroidApp.databinding.ActivityMovieDetailBinding
import com.example.firstAndroidApp.movieSearch.data.MovieRepository
import com.example.firstAndroidApp.movieSearch.data.RetrofitBuilder
import com.example.firstAndroidApp.movieSearch.model.MovieDetails
import com.example.firstAndroidApp.movieSearch.viewModel.MovieViewModel
import com.example.firstAndroidApp.movieSearch.viewModel.ViewModelFactory
import com.example.firstAndroidApp.util.GlideUtil

class MovieDetail : AppCompatActivity() {

    companion object {
        const val API_LOG_TAG_DETAIL = "MOVE_DETAIL"
        private const val IMDB_ID = "imdbID"
    }

    private lateinit var movieVM: MovieViewModel

    private lateinit var viewBinding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupViewModel()
        attachObserver()
        getData()
    }

    private fun setupViewModel() {
        val apiInterface = RetrofitBuilder.getRetrofit().create(MoviesAPI::class.java)
        val movieRepository = MovieRepository(apiInterface)
        movieVM =
            ViewModelProvider(this, ViewModelFactory(movieRepository))[MovieViewModel::class.java]
    }

    private fun attachObserver() {
        movieVM.movieDetails.observe(this, {
            updateData(it)
        })
    }

    private fun getData() {
        val intent = intent
        val search = intent.getStringExtra(IMDB_ID)

        if (search != null) {
            movieVM.getMovieDetails(search)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateData(result: MovieDetails) {
        viewBinding.movieTitle.text = result.Title
        viewBinding.Genre.text = "Genre: ${result.Genre}"
        viewBinding.Actors.text = "Actors: ${result.Actors}"
        viewBinding.Director.text = "Director: ${result.Director}"
        viewBinding.Released.text = "Release: ${result.Released}"
        viewBinding.imdbRating.text = "IMDB Rating: ${result.imdbRating}"
        viewBinding.Country.text = "Country: ${result.Country}"
        viewBinding.plot.text = "Plot: ${result.Plot}"
        viewBinding.runtime.text = "Runtime: ${result.Runtime}"
        viewBinding.awards.text = "Awards: ${result.Awards}"

        result.Poster.let {
            GlideUtil.setImage(
                this@MovieDetail,
                it, viewBinding.moviePoster
            )
        }

        viewBinding.progressBar.visibility = View.GONE
        viewBinding.linearLayout.visibility = View.VISIBLE
        viewBinding.movieTitle.visibility = View.VISIBLE
    }
}