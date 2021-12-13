package com.example.firstAndroidApp.movieSearch.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstAndroidApp.R
import com.example.firstAndroidApp.movieSearch.data.MovieRepository
import com.example.firstAndroidApp.movieSearch.ui.adapter.MovieAdapter
import com.example.firstAndroidApp.movieSearch.ui.view.MovieDetail
import com.example.firstAndroidApp.movieSearch.data.MoviesAPI
import com.example.firstAndroidApp.movieSearch.data.RetrofitBuilder
import com.example.firstAndroidApp.movieSearch.model.Movie
import com.example.firstAndroidApp.movieSearch.viewModel.MovieViewModel
import com.example.firstAndroidApp.movieSearch.viewModel.ViewModelFactory


class MovieSearchFragment : Fragment(), MovieAdapter.OnMovieListener {

    companion object {
        const val API_LOG_TAG = "MOVE_SEARCH_FRAGMENT"
        private const val IMDB_ID = "imdbID"
    }

    private lateinit var movieVM: MovieViewModel

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

        setupSearch()
        initRV()
        setupViewModel()
        attachObserver()
    }

    private fun attachObserver() {
        movieVM.movie.observe(viewLifecycleOwner, {
            updateDataRV(it)
        })
    }

    private fun setupViewModel() {
        val apiInterface = RetrofitBuilder.getRetrofit().create(MoviesAPI::class.java)
        val movieRepository = MovieRepository(apiInterface)
        movieVM =
            ViewModelProvider(this, ViewModelFactory(movieRepository))[MovieViewModel::class.java]
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

    private fun getData(search: String) {
        if (search.isNotEmpty()) {
            movieVM.getMovies(search)
        }
    }

    private fun updateDataRV(movieList: List<Movie>) {
        mAdapter.updateData(movieList)
    }

    override fun onMovieClick(imdbId: String) {
        val intent = Intent(context, MovieDetail::class.java)
        intent.putExtra(IMDB_ID, imdbId)
        startActivity(intent)
    }

}
