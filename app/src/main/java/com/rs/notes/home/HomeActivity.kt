package com.rs.notes.home

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rs.notes.Mydatabase
import com.rs.notes.R
import com.rs.notes.addnotes.AddNotesActivity
import com.rs.notes.base.BaseActivity
import com.rs.notes.databinding.ActivityHomeBinding


class HomeActivity  : BaseActivity<ActivityHomeBinding, HomeViewModel>() {


    var al: ArrayList<*>? = null
    var keys = arrayOf("k1", "k2", "k3", "k4", "k5")

    var biometricPrompt: BiometricPrompt? = null
    var promptInfo: PromptInfo? = null

    var authenticated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        init()
        initControl()

        initNotesRv()

        readdata()

        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(
                this,
                "Device Doesn't have fingerprint",
                Toast.LENGTH_SHORT
            ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "No Finger Print Assigned", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Toast.makeText(
                this,
                "No Finger Print Assigned",
                Toast.LENGTH_SHORT
            ).show()
        }

        val executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Toast.makeText(MainActivity2.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@HomeActivity, "Authentication Success", Toast.LENGTH_SHORT)
                        .show()

                   binding.constraint.setVisibility(View.VISIBLE)

                    authenticated = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    //   Toast.makeText(MainActivity2.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            })

        promptInfo = PromptInfo.Builder().setTitle("Required Authentication")
            .setDescription("For security purpose, we made compulsory to authenticate to use the APP")
            .setDeviceCredentialAllowed(true).build()

        biometricPrompt!!.authenticate(promptInfo!!)


    }

    override val layoutRes: Int
        get() = R.layout.activity_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun bindViewModel() {
        binding.homeViewModelBinding = viewModel
        binding.lifecycleOwner = this
    }

    override fun init() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length != 0) {

                    binding.rvNotes.removeAllViews()
                    val adp =NotesAdapter(this@HomeActivity)
                    binding.rvNotes.adapter = adp
                    adp.setData(al as java.util.ArrayList<Any?>, s.toString())
                    adp.notifyDataSetChanged()

                }else {

                    binding.rvNotes.removeAllViews()
                    val adp =NotesAdapter(this@HomeActivity)
                    binding.rvNotes.adapter = adp
                    adp.setData(al as java.util.ArrayList<Any?>, s.toString())
                    adp.notifyDataSetChanged()
                }

            }
        })
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

    private fun initNotesRv() {

        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.setLayoutManager(
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
        )

    }

    fun readdata() {
        al = java.util.ArrayList<Any?>()
        (al as java.util.ArrayList<Any?>).clear()

        val md = Mydatabase(this)
        val sd: SQLiteDatabase = md.getReadableDatabase()
        val cursor = sd.query(Mydatabase.TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val title = cursor.getString(1)
                val desc = cursor.getString(2)
                val created_at = cursor.getString(3)
                val updated_at = cursor.getString(4)
                var hm : HashMap<String, String>
                        = HashMap<String, String> ()

                hm[keys[0]] = id
                hm[keys[1]] = title
                hm[keys[2]] = desc
                hm[keys[3]] = created_at
                hm[keys[4]] = updated_at
                (al as java.util.ArrayList<Any?>).add(hm)
                //val hm2 = (al as java.util.ArrayList<Any?>).get(0) as java.util.HashMap<*, *>

                binding.rvNotes.removeAllViews()
                val adp = NotesAdapter(this)
                binding.rvNotes.adapter = adp
                adp.setData(al as java.util.ArrayList<Any?>, "")
                adp.notifyDataSetChanged()
               // Toast.makeText(this, ""+hm2[keys[1]], Toast.LENGTH_SHORT).show()
            } while (cursor.moveToNext())
        } else {
          //  Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            binding.rvNotes.removeAllViews()
            val adp = NotesAdapter(this)
            binding.rvNotes.adapter = adp
            adp.setData(al as java.util.ArrayList<Any?>, "")
            adp.notifyDataSetChanged()
        }
    }


     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.menu_add, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle item selection
         return when (item.itemId) {
             R.id.ivAdd -> {

                 if (authenticated){
                     val intent = Intent(this, AddNotesActivity::class.java)
                     intent.putExtra("from","insert")
                     startActivity(intent)
                 }
                 else {
                     Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()

                     promptInfo = PromptInfo.Builder().setTitle("Required Authentication")
                         .setDescription("For security purpose, we made compulsory to authenticate to use the APP")
                         .setDeviceCredentialAllowed(true).build()

                     biometricPrompt!!.authenticate(promptInfo!!)
                    // onBackPressed()
                 }

                 //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
                 true
             }

             else -> super.onOptionsItemSelected(item)
         }
     }


    override fun onResume() {
        super.onResume()

        readdata()
    }

    fun edit(id: String?, title: String?, desc: String?, created: String?, updated: String?) {

        val intent = Intent(this, AddNotesActivity::class.java)
        intent.putExtra("from","edit")
        intent.putExtra("id",id)
        intent.putExtra("title",title)
        intent.putExtra("desc",desc)
        intent.putExtra("created",created)
        intent.putExtra("updated",updated)
        startActivity(intent)
    }

    fun delete(s: String?) {

        val md = Mydatabase(this)
        val db = md.readableDatabase


        val count = db.delete(Mydatabase.TABLE_NAME, "ID = ?", arrayOf(s))

        if (count > 0) {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            readdata()
        } else {
            Toast.makeText(this, "delete failed", Toast.LENGTH_SHORT).show()
        }
    }

}


