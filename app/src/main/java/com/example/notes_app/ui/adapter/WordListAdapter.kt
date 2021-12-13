package com.example.notes_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.notes_app.R
import com.example.notes_app.data.room.Notes
import kotlin.collections.ArrayList


class WordListAdapter(wordList: ArrayList<Notes>, private var onNoteListener: OnNoteListener) :
    RecyclerView.Adapter<WordListAdapter.NotesViewHolder>() {

    private val mNotesList: ArrayList<Notes> = wordList;

    interface OnNoteListener {
        fun onNoteClick(position: Int)
        fun onDeleteClick(position: Int)

    }

    class NotesViewHolder(
        itemView: View,
        adapter: WordListAdapter,
        onNoteListener: OnNoteListener
    ) :
        RecyclerView.ViewHolder(itemView) {
        val notesTitle: TextView = itemView.findViewById(R.id.title)
        val notesDesc: TextView = itemView.findViewById(R.id.desc)
        val notesDate: TextView = itemView.findViewById(R.id.dateofnote)
        private val mAdapter: WordListAdapter

        init {
            val card = itemView.findViewById<CardView>(R.id.cardView);
            val deleteNote = itemView.findViewById<Button>(R.id.delete_note)
            mAdapter = adapter
            deleteNote.setOnClickListener {
                onNoteListener.onDeleteClick(layoutPosition)
            }
            card.setOnClickListener {
                onNoteListener.onNoteClick(layoutPosition);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        // Inflate an item view.
        val mItemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.wordlist_item, parent, false
        )
        return NotesViewHolder(mItemView, this, onNoteListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        // Retrieve the data for that position.
        val mCurrent = mNotesList[position]
        // Add the data to the view holder.
        holder.notesTitle.text = mCurrent.title;
        holder.notesDesc.text = mCurrent.desc;
        holder.notesDate.text = mCurrent.time
    }

    override fun getItemCount(): Int {
        return mNotesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(mNotesLists: List<Notes>) {
        mNotesList.clear()
        mNotesList.addAll(mNotesLists)
        notifyDataSetChanged()
    }
}