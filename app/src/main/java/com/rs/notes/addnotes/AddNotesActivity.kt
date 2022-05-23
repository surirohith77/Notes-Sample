package com.rs.notes.addnotes

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.rs.notes.Mydatabase
import com.rs.notes.R
import com.rs.notes.base.BaseActivity
import com.rs.notes.databinding.ActivityAddNotesBinding
import com.rs.notes.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.*


class AddNotesActivity  : BaseActivity<ActivityAddNotesBinding, AddNotesViewModel>() {

    var from = ""
    var id :Int?=null
    var title = ""
    var desc = ""
    var created = ""
    var updated = ""
    var currentDateandTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        init()
        initControl()

        from  = intent.getStringExtra("from").toString()
        if (from.equals("edit")){

            id  = intent.getStringExtra("id").toString().toInt()
            title  = intent.getStringExtra("title").toString()
            desc  = intent.getStringExtra("desc").toString()
            created  = intent.getStringExtra("created").toString()
            updated  = intent.getStringExtra("updated").toString()

            binding.etDesc.setText(desc)
            binding.etTitle.setText(title)
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
         currentDateandTime = sdf.format(Date())
     //  Toast.makeText(this, ""+currentDateandTime, Toast.LENGTH_SHORT).show()
    }


    override val layoutRes: Int
        get() = R.layout.activity_add_notes

    override val viewModelClass: Class<AddNotesViewModel>
        get() = AddNotesViewModel::class.java

    override fun bindViewModel() {
        binding.addNotesViewModelBinding = viewModel
        binding.lifecycleOwner = this
    }

    override fun init() {

    }

    override fun initControl() {
        inItLiveDataObserver()
    }

    private fun inItLiveDataObserver() {
        viewModel.onCLick.observe(this) {
            when (it.id) {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_file, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.ivDone -> {

                if (from.equals("insert")){
                    saveNotes()
                }
                else{
                    updateNotes()
                }

                //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }



    fun saveNotes(){


       val cv = ContentValues()

       cv.put(Mydatabase.COL2, binding.etTitle.text.toString())
       cv.put(Mydatabase.COL3, binding.etDesc.text.toString())
       cv.put(Mydatabase.COL4, currentDateandTime)
       cv.put(Mydatabase.COL5, "")


       val md = Mydatabase(this)
       val db: SQLiteDatabase = md.getWritableDatabase()

       val result = db.insert(Mydatabase.TABLE_NAME, null, cv)
       if (result != -1L) {

           Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()

//           val intent = Intent(this, HomeActivity::class.java)
//           startActivity(intent)
//           finish()

           onBackPressed()

       } else {
           Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show()
       }

   }


    private fun updateNotes() {
        val md = Mydatabase(this)
        val sd = md.writableDatabase

        val cv = ContentValues()

        cv.put(Mydatabase.COL2, binding.etTitle.text.toString())
        cv.put(Mydatabase.COL3, binding.etDesc.text.toString())
       // cv.put(Mydatabase.COL4, "")
        cv.put(Mydatabase.COL5, currentDateandTime)

        val count = sd.update("employee_details", cv, "id=?", arrayOf(id.toString()))

        if (count > 0) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()

//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()

            onBackPressed()

        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }
}