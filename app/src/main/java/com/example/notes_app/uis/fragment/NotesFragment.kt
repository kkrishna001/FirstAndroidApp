package com.example.notes_app.uis.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.EditNote
import com.example.notes_app.R
import com.example.notes_app.uis.adapter.WordListAdapter
import com.example.notes_app.uis.view.ChangeUser
import com.example.notes_app.uis.view.NotesHandler
import com.example.notes_app.data.room.Notes
import com.example.notes_app.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList


class NotesFragment : Fragment(), WordListAdapter.OnNoteListener {

    companion object {
        private const val EDIT_USER_REQUEST_CODE: Int = 1
        private const val ADD_NOTE_REQUEST_CODE: Int = 2
        private const val UPDATE_NOTE_REQUEST_CODE: Int = 3
        private const val CHANGE_PROFILE_REQUEST_CODE: Int = 4
        private const val IMAGE_TITLE = "Select Image"
        private const val NAME_KEY = "NAME_KEY"
        private const val EMAIL_KEY = "EMAIL_KEY"
        private const val EDITNOTE_KEY = "EDITNOTE_KEY"
        private const val EDITPOS_KEY = "EDITPOS_KEY"
        private const val NOTE_KEY = "NOTE_KEY"
        private const val IMAGE_TYPE = "image/*"
    }

    private var mNotesList = ArrayList<Notes>()

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: WordListAdapter

    private lateinit var userName: TextView

    private lateinit var userEmail: TextView

    private lateinit var noteVM: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notes, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFabButton()
        setupUserNameAndEmail()
        setupProfileFabButton()
        setupFabButton()
        setupEditUser()
        onLoadData()
        setupRV()
        initViewModel()
        attachObserver()
    }

    private fun setupFabButton() {
        val fab = view?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {

            val intent = Intent(context, NotesHandler::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }
    }

    private fun setupProfileFabButton() {
        val profileFab =
            view?.findViewById<FloatingActionButton>(R.id.changeProfileImage)
        profileFab?.setOnClickListener {
            val gallery = Intent()
            gallery.type = IMAGE_TYPE
            gallery.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(gallery, IMAGE_TITLE),
                CHANGE_PROFILE_REQUEST_CODE
            )
        }
    }

    private fun setupEditUser() {
        val editUser = view?.findViewById<Button>(R.id.edit_user)
        editUser?.setOnClickListener {
            val intent = Intent(context, ChangeUser::class.java)

            intent.putExtra(NAME_KEY, userName.text)
            intent.putExtra(EMAIL_KEY, userEmail.text)
            startActivityForResult(intent, EDIT_USER_REQUEST_CODE)
        }
    }

    private fun setupRV() {
        mRecyclerView = view?.findViewById(R.id.recyclerview)!!
        mAdapter = WordListAdapter(mNotesList, this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel() {
        noteVM = ViewModelProvider(this)[NoteViewModel::class.java]
    }

    private fun attachObserver() {
        noteVM.allNotes.observe(viewLifecycleOwner, { list ->
            list?.let {
                mAdapter.updateData(it)
            }
        })
    }

    private fun setupUserNameAndEmail() {
        userName = view?.findViewById(R.id.UserName)!!
        userEmail = view?.findViewById(R.id.email)!!
    }

    /*
        1.Create util package.
        2.Create file SharedPreferenceUtil

        object SharedPreferenceUtil {
            fun getSharedPrefInstance() : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

            fun SharedPreferenceUtil.getString(key: String, defaultValue: String?) : String? {
                return getSharedPrefInstance().getString("NAME_KEY1",null)
            }
        }

    */
    private fun onLoadData() {

//        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)//same for shared-prefs.
//        val savedName: String? =
//            sharedPreferences.getString(NAME_KEY, null)
//        val savedEmail: String? =
//            sharedPreferences.getString(NAME_KEY, null)//extract in constants
//        userName.text = savedName
//        userEmail.text = savedEmail

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_USER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                val name: String? = data?.getStringExtra(NAME_KEY)
//                val email: String? = data?.getStringExtra(EMAIL_KEY)
//
//                val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)//same for shared-prefs.
//
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                editor.apply {
//                    putString(NAME_KEY, name)
//                    putString(EMAIL_KEY, email)
//                }.apply()

//                userName.text = name
//                userEmail.text = email
            }
        } else if (requestCode == ADD_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val note: Notes = data?.getSerializableExtra(NOTE_KEY) as Notes
                val wordListSize = mNotesList.size
                mNotesList.add(note)
                noteVM.addNote(note)
                println(mNotesList)
                mAdapter.notifyItemInserted(wordListSize)
                mRecyclerView.smoothScrollToPosition(wordListSize)
            }
        } else if (requestCode == UPDATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val note: Notes =
                    data?.getSerializableExtra(EDITNOTE_KEY) as Notes
                val position: Int = data.getIntExtra(EDITPOS_KEY, 0)
                mNotesList[position].title = note.title
                mNotesList[position].desc = note.desc
                noteVM.updateNote(mNotesList[position])
                mAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == CHANGE_PROFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                val uri: Uri? = data?.data
                val imageView: CircleImageView? = view?.findViewById(R.id.userProfile)

                imageView?.setImageURI(uri)
            }
        }
    }

    override fun onNoteClick(position: Int) {
        val note = mNotesList[position]
        println(note.title)
        println(note.desc)
        val intent = Intent(context, EditNote::class.java)
        intent.putExtra(EDITNOTE_KEY, note)
        intent.putExtra(EDITPOS_KEY, position)
        startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteClick(position: Int) {
        val notes = mNotesList[position]
        noteVM.deleteNote(notes)
        mNotesList.removeAt(position)
        mAdapter.notifyDataSetChanged()
    }
}