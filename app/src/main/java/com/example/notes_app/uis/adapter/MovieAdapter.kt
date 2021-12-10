package com.example.notes_app.uis.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.notes_app.R
import com.example.notes_app.model.Movie
import kotlin.collections.ArrayList


class MovieAdapter(wordList: ArrayList<Movie>, private var onNoteListener: OnMovieListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var activity: Context

    private val movieList: ArrayList<Movie> = wordList

    interface OnMovieListener {
        fun onMovieClick(imdbId: String)

    }

    inner class MovieViewHolder(
        itemView: View,
        adapter: MovieAdapter,
        onMovieListener: OnMovieListener
    ) :
        RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
        val movieTitle: TextView = itemView.findViewById(R.id.title)
        val movieYear: TextView = itemView.findViewById(R.id.year)

        private val mAdapter: MovieAdapter

        init {
            val card = itemView.findViewById<CardView>(R.id.cardView)
            mAdapter = adapter
            card.setOnClickListener {
                onMovieListener.onMovieClick(movieList[layoutPosition].imdbID)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        activity = parent.context
        val mItemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )
        return MovieViewHolder(mItemView, this, onNoteListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val mCurrent = movieList[position]
        holder.movieTitle.text = mCurrent.Title
        holder.movieYear.text = mCurrent.Year
        Glide.with(activity).load(mCurrent.Poster).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.moviePoster)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(movieLists: List<Movie>) {
        movieList.clear()
        movieList.addAll(movieLists)
        notifyDataSetChanged()
    }

}