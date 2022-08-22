package com.androidimportantconcepts

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.androidimportantconcepts.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private val TAG = "MainActivity"
    private var firstTimeLoaded = false

    val mColumnProjection = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.Contacts.CONTACT_STATUS,
        ContactsContract.Contacts.HAS_PHONE_NUMBER
    )

    val mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + "= ?"
    val mSelectionArgs = arrayOf("Police")
    val mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    val contacts: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getContactsBtn.setOnClickListener {
            if (!firstTimeLoaded) {
                LoaderManager.getInstance(this).initLoader(1, null, this)
                firstTimeLoaded = true
            } else {
                LoaderManager.getInstance(this).restartLoader(1, null, this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(
                    this,
                    "Until you grant the permission, we canot display the names",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private fun showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
//            val contacts = getContactNames()
            Log.e(TAG, "list of contact : $contacts")
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if (id == 1) {
            return CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection,
                null,
                null,
                mOrderBy
            )
        }
        return CursorLoader(this)
    }

    @SuppressLint("Range")
    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor != null && cursor.count > 0) {
            contacts.clear()
            if (cursor.moveToFirst()) {
                // Iterate through the cursor
                do {
                    // Get the contacts name
                    val name: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    contacts.add(name)
                } while (cursor.moveToNext())
                Log.e(TAG, "list of contact onLoadFinished : $contacts")
            }
//            cursor.close()
        } else {
            Toast.makeText(this, "No Contacts", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Log.e(TAG,"on loader reset called")
    }
}