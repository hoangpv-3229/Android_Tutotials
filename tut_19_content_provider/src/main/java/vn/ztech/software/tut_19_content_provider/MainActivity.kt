package vn.ztech.software.tut_19_content_provider

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var lv: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lv)

        showContactList()
    }

    private fun showContactList() {
        if (isPermissionGranted()){
           getContactsAndBindData()
        }
    }
    private fun getContactsAndBindData(){
        val contacts = getContacts()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts)
        lv.adapter = adapter
    }

    private fun isPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS)){
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("Read Contacts permission")
                        builder.setPositiveButton(android.R.string.ok, null)
                        builder.setMessage("Please enable access to contacts.")
                        builder.setOnDismissListener(DialogInterface.OnDismissListener {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.READ_CONTACTS
                                ), 1000
                            )
                        })
                        builder.show()
                        return false
                    }else{
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_CONTACTS
                            ), 1000
                        )
                        return false
                    }
                }else return true
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getContactsAndBindData()
                }else{
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private fun getContacts(): List<String> {
        val NAME_COLLUM = ContactsContract.Contacts.DISPLAY_NAME
        val PHONE_COLLUM = ContactsContract.CommonDataKinds.Phone.NUMBER

        val contacts = mutableListOf<String>()
        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOf(NAME_COLLUM, PHONE_COLLUM), null, null, NAME_COLLUM)
        while (cursor?.moveToNext()?:false){
//            val nameIdx = cursor?.getColumnIndex(NAME_COLLUM)?:0
//            val phoneIdx = cursor?.getColumnIndex(PHONE_COLLUM)?:1

            val name = cursor?.getString(0)
            val phone = cursor?.getString(1)

            contacts.add("${name} $phone")
        }
        return contacts
    }
}