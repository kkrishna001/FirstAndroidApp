package com.example.notes_app


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URI
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),WordListAdapter.OnNoteListener{



    lateinit var database: NoteDatabase

    private var mNotesList = ArrayList<Notes>()

    private var mRecyclerView: RecyclerView? = null

    private var mAdapter: WordListAdapter? = null

    private var userName:TextView?=null;

    private var userEmail:TextView?=null;

    private lateinit var userProfile:CircleImageView;

    lateinit var noteVM:NoteViewModel

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar= findViewById<Toolbar>(R.id.toolbar);

        val profileFab=findViewById<FloatingActionButton>(R.id.changeProfileImage)

        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        //setting up profile
            userName=findViewById(R.id.UserName)
            userProfile=findViewById(R.id.userProfile)
            userEmail=findViewById(R.id.email);
            val edituser= findViewById<Button>(R.id.edit_user);
            edituser.setOnClickListener{
                val intent = Intent(this,ChangeUser::class.java)


                val name:TextView=findViewById(R.id.UserName)
                val email:TextView=findViewById(R.id.email)
                intent.putExtra("NAME_KEY",name.text)
                intent.putExtra("EMAIL_KEY",email.text)
                ActivityCompat.startActivityForResult(this,intent,1,null);
            }

            profileFab.setOnClickListener{
                val gallery=Intent()
                gallery.setType("image/*")
                gallery.setAction(Intent.ACTION_GET_CONTENT)

               ActivityCompat.startActivityForResult(this,Intent.createChooser(gallery,"Select Image"),4,null)

            }
        onloaddata()
        //--------------
        //setting up Notes Addition
        fab.setOnClickListener {
           val intent=Intent(this,NotesHandler::class.java)

            ActivityCompat.startActivityForResult(this,intent,2,null);
        }
        mRecyclerView = findViewById(R.id.recyclerview)
        mAdapter = WordListAdapter(this,mNotesList,this)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)

        noteVM = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance((application)))[NoteViewModel::class.java]

        noteVM.allnotes.observe(this, Observer { list->
            list?.let{
                mAdapter?.updateData(it)
            }
        })
    }

    private fun onloaddata(){
        val sharedPreferences:SharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedname:String?=sharedPreferences.getString("NAME_KEY1",null)
        val savedEmail:String?=sharedPreferences.getString("EMAIL_KEY1",null)

        userName?.text=savedname
        userEmail?.text=savedEmail


    }
    var imageUri:Uri?=null
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                val name: String? = data?.getStringExtra("NAME_KEY");
                val email:String?=data?.getStringExtra("EMAIL_KEY");

                val sharedPreferences:SharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =sharedPreferences.edit()
                editor.apply {
                    putString("NAME_KEY1",name);
                    putString("EMAIL_KEY1",email)
                }.apply()
                userName?.text = name;
                userEmail?.text=email;
            }
        }
        else if(requestCode==2)
        {
            if(resultCode==RESULT_OK)
            {
                val note:Notes= data?.getSerializableExtra("NOTE_KEY") as Notes
                val wordListSize = mNotesList.size
                // Add a new word to the wordList.
                mNotesList.add(note)

                noteVM.addNote(note);
                // Notify the adapter, that the data has changed.
                mRecyclerView!!.adapter!!.notifyItemInserted(wordListSize)
                // Scroll to the bottom.
                mRecyclerView!!.smoothScrollToPosition(wordListSize)
            }
        }
        else if(requestCode==3)
        {
            if(resultCode== RESULT_OK)
            {
                val note:Notes=data?.getSerializableExtra("EDITNOTE_KEY") as Notes
                val position:Int=data.getIntExtra("EDITPOS_KEY",0) as Int

                mNotesList[position].title=note.title
                mNotesList[position].desc=note.desc

                noteVM.updateNote(mNotesList[position])
                mRecyclerView!!.adapter!!.notifyDataSetChanged();
            }
        }
        else if(requestCode==4)
        {

            if(resultCode == RESULT_OK)
            {
                imageUri=data?.getData()
                try {
                    val bitmap:Bitmap=MediaStore.Images.Media.getBitmap(contentResolver,imageUri)
                    userProfile.setImageBitmap(bitmap)
                }
                catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onNoteClick(position: Int) {
        val note=mNotesList[position]
        println(note.title)
        println(note.desc)
        val intent = Intent(this,EditNote::class.java)
        intent.putExtra("EDITNOTE_KEY",note)
        intent.putExtra("EDITPOS_KEY",position)
        ActivityCompat.startActivityForResult(this,intent,3,null)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteClick(position: Int) {

        val notes=mNotesList[position]
//        lifecycleScope.launch(Dispatchers.IO){
//            database.getNoteDao().deleteNote(notes)
//        }
        noteVM.deleteNote(notes)
        mNotesList.removeAt(position)
        mRecyclerView!!.adapter!!.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        // This comment suppresses the Android Studio warning about simplifying
//        // the return statements.
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//    }




//    val searchView=findViewById<SearchView>(R.id.searchNotes);
//    searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
//        override fun onQueryTextSubmit(query: String?): Boolean {
////
//            return true;
//        }
//        override fun onQueryTextChange(newText: String?): Boolean {
//            if(newText!!.isNotEmpty())
//            {
//                mNotesList.clear();
//
//                val search = newText.toLowerCase(Locale.getDefault());
//                tempArrayList.forEach {
//                    if(it.contains(search,true))
//                    {
//                        mNotesList.add(it);
//                    }
//                }
////                    Log.d("searchbar",newText)
////                    println(mNotesList);
//                mRecyclerView!!.adapter!!.notifyDataSetChanged();
//            }
//            else
//            {
//                mNotesList.clear();
//                mNotesList.addAll(tempArrayList);
//                mRecyclerView!!.adapter!!.notifyDataSetChanged();
//            }
//            return true;
//        }
//    });
}