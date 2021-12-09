package com.example.notes_app.UI.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.*
import com.example.notes_app.UI.adapter.WordListAdapter
import com.example.notes_app.data.room.Notes
import com.example.notes_app.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), WordListAdapter.OnNoteListener {
    
    
    // TODO(KK) Resolve comments.
    // Create fragment.
    // copy activitys code inside fragment.
    // create an empty layout for fragment holder// framelayout + Bottom navigation view. (Notes + Search Movie) // Jetpack navigation
    

    //move these request codes inside companion object and declare them constants. ALL 
    private val firstRequestCode:Int=1
    private val secondRequestCode:Int=2
    private val thirdRequestCode:Int=3
    private val fourthRequestCode:Int=4


    private var mNotesList = ArrayList<Notes>()

    private var mRecyclerView: RecyclerView? = null

    private var mAdapter: WordListAdapter? = null

    private var userName:TextView?=null;

    private var userEmail:TextView?=null;

    private lateinit var userProfile:CircleImageView;//create this nullable

    lateinit var noteVM: NoteViewModel

    @SuppressLint("CutPasteId")//remove
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        //extract this part in a function. --start
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
           val intent=Intent(this, NotesHandler::class.java)

            ActivityCompat.startActivityForResult(this,intent,secondRequestCode,null);
        }
        //---end
        //correct indentation
            userName=findViewById(R.id.UserName)
            userProfile=findViewById(R.id.userProfile)
            userEmail=findViewById(R.id.email)
            val edituser= findViewById<Button>(R.id.edit_user)
            edituser.setOnClickListener{
                val intent = Intent(this, ChangeUser::class.java)


                val name:TextView=findViewById(R.id.UserName)
                val email:TextView=findViewById(R.id.email)
                intent.putExtra("NAME_KEY",name.text)
                intent.putExtra("EMAIL_KEY",email.text)
                ActivityCompat.startActivityForResult(this,intent,firstRequestCode,null);
            }
        //correct indentation
        //extract in function --start
        val profileFab=findViewById<FloatingActionButton>(R.id.changeProfileImage)//remove if not needed
            profileFab.setOnClickListener{
                val gallery=Intent()
                gallery.setType("image/*")
                gallery.setAction(Intent.ACTION_GET_CONTENT)

               ActivityCompat.startActivityForResult(this,Intent.createChooser(gallery,"Select Image"),fourthRequestCode,null)

            }
        //--end
        onloaddata()

        //extract this in function //setupRecyclerview --start
        mRecyclerView = findViewById(R.id.recyclerview)
        mAdapter = WordListAdapter(this,mNotesList,this)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        //--end
        
        //extract this piece.--start --  initViewModel()
        noteVM = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance((application)))[NoteViewModel::class.java]
        //--end
        
        //extract this piece -start --attachObserservere
        noteVM.allnotes.observe(this, Observer { list->
            list?.let{
                mAdapter?.updateData(it)
            }
        })
        //--end
    }

    
    /*
        1.Create util package.
        2.Create file SharedPreferennceUtil
        
        object SharedPreferennceUtil {
            fun getSharedPrefInstance() : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            
            fun SharedPreferennceUtil.getString(key: String, defaultValue: String?) : String? {
                return getSharedPrefInstance().getString("NAME_KEY1",null)
            }
        }
    
    */
    private fun onloaddata(){
        val sharedPreferences:SharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)//extract in constants
        val savedname:String?=sharedPreferences.getString("NAME_KEY1",null)//extract in constants
        val savedEmail:String?=sharedPreferences.getString("EMAIL_KEY1",null)//extract in constants

        userName?.text=savedname
        userEmail?.text=savedEmail


    }
    
    //move above functions if a class level variable, if not move inside function.
    var imageUri:Uri?=null
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                val name: String? = data?.getStringExtra("NAME_KEY");//extract constants
                val email:String?=data?.getStringExtra("EMAIL_KEY");//extract constants

                val sharedPreferences:SharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)//same for sharedprefs.
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
                val note: Notes = data?.getSerializableExtra("NOTE_KEY") as Notes//extract constants
                val wordListSize = mNotesList.size
                // Add a new word to the wordList.
                mNotesList.add(note)

                noteVM.addNote(note);
                // Notify the adapter, that the data has changed.// use mAdapter instead of asking recyclerviews adapter.
                mRecyclerView!!.adapter!!.notifyItemInserted(wordListSize)
                // Scroll to the bottom.
                mRecyclerView!!.smoothScrollToPosition(wordListSize)
            }
        }
        else if(requestCode==3)
        {
            if(resultCode== RESULT_OK)
            {
                val note: Notes =data?.getSerializableExtra("EDITNOTE_KEY") as Notes //extract constants.
                val position:Int=data.getIntExtra("EDITPOS_KEY",0) as Int //extract constants

                mNotesList[position].title=note.title
                mNotesList[position].desc=note.desc

                noteVM.updateNote(mNotesList[position])
                mRecyclerView!!.adapter!!.notifyDataSetChanged();
            }
        }
        else if(requestCode==4) //extract constants
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
        val intent = Intent(this, EditNote::class.java)
        intent.putExtra("EDITNOTE_KEY",note) 
        intent.putExtra("EDITPOS_KEY",position)
        ActivityCompat.startActivityForResult(this,intent,thirdRequestCode,null)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteClick(position: Int) {

        val notes=mNotesList[position]

        noteVM.deleteNote(notes)
        mNotesList.removeAt(position)
        mRecyclerView!!.adapter!!.notifyDataSetChanged()
    }
    
    //delete commented code.
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
