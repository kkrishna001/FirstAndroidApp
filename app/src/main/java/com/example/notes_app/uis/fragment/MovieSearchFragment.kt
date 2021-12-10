package com.example.notes_app.uis.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.R
import com.example.notes_app.uis.adapter.MovieAdapter
import com.example.notes_app.uis.view.MovieDetail
import com.example.notes_app.data.API
import com.example.notes_app.model.Movie
import com.example.notes_app.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieSearchFragment : Fragment(), MovieAdapter.OnMovieListener {

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com"
        private const val API_KEY = "1ddee4bf"
        private const val API_LOG_TAG = "MOVE_SEARCH_FRAGMENT"
        private const val IMDB_ID = "imdbID"
    }

    private lateinit var retrofitBuilder: API

    private val moviesList = ArrayList<Movie>()

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrofitBuilder()
        setupSearch()
        initRV()
    }

    private fun initRV() {
        mRecyclerView = view?.findViewById(R.id.recyclerView)!!
        mAdapter = MovieAdapter(moviesList, this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSearch() {
        val searchNow = view?.findViewById<SearchView>(R.id.searchBarMovie)
        searchNow?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    //debounce
                    getData(newText)
                } else {
                    mAdapter.updateData(emptyList())
                }
                return true
            }
        })
    }

    private fun retrofitBuilder() {
        retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL
            ).build().create(API::class.java)
    }

    private fun getData(search: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val retrofitData = retrofitBuilder.getMovies(search, API_KEY)
            retrofitData.enqueue(object : Callback<MoviesResponse?> {

                override fun onResponse(
                    call: Call<MoviesResponse?>,
                    response: Response<MoviesResponse?>
                ) {
                    val result = response.body()
                    if (result != null && result.Response.equals("true", ignoreCase = true)) {
                        mAdapter.updateData(result.Search)
                    }
                }

                override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                    Log.d(API_LOG_TAG, "onFailure" + t.message)
                    mAdapter.updateData(emptyList())
                }
            })
        }
    }

    override fun onMovieClick(imdbId: String) {
        val intent = Intent(context, MovieDetail::class.java)
        intent.putExtra(IMDB_ID, imdbId)
        startActivity(intent)
    }
}
