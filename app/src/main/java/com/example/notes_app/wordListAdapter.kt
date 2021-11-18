package com.example.notes_app

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlin.collections.ArrayList



class WordListAdapter(context: Context, wordList: ArrayList<Notes>,private var onNoteListener:OnNoteListener) :
    RecyclerView.Adapter<WordListAdapter.NotesViewHolder>() {
    private val mNotesList: ArrayList<Notes> = wordList;
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    interface OnNoteListener{
        fun onNoteClick(position: Int)
        fun onDeleteClick(position:Int)
    }
    inner class NotesViewHolder(
        itemView: View,
        adapter: WordListAdapter,
        onNoteListener: OnNoteListener
    ):
        RecyclerView.ViewHolder(itemView){
        val notesTitle:TextView = itemView.findViewById(R.id.title)
        val notesDesc:TextView = itemView.findViewById(R.id.desc)
        val notesDate:TextView = itemView.findViewById(R.id.dateofnote)
        private val mAdapter: WordListAdapter
        init {
            val card=itemView.findViewById<CardView>(R.id.cardView);
            val delete_note=itemView.findViewById<Button>(R.id.delete_note)
            mAdapter = adapter
//            val note=Notes();
            delete_note.setOnClickListener{
                onNoteListener.onDeleteClick(layoutPosition)
            }
            card.setOnClickListener{
                onNoteListener.onNoteClick(layoutPosition);
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        // Inflate an item view.
        val mItemView: View = mInflater.inflate(
            R.layout.wordlist_item, parent, false
        )
        return NotesViewHolder(mItemView,this,onNoteListener)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        // Retrieve the data for that position.
        val mCurrent = mNotesList[position]
        // Add the data to the view holder.
        holder.notesTitle.text = mCurrent.title;
        holder.notesDesc.text = mCurrent.desc;
        holder.notesDate.text = mCurrent.time.toString();
    }
    override fun getItemCount(): Int {
        return mNotesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(mNotesLists: ArrayList<Notes>) {
        mNotesList.clear()
        mNotesList.addAll(mNotesLists)
        notifyDataSetChanged()
    }
}