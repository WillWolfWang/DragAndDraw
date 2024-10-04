package com.will.draganddraw.contact

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.will.draganddraw.databinding.ActivityContactBinding

/**
 * 读取联系人列表
 */
class ContactActivity: AppCompatActivity() {

    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewBinding: ActivityContactBinding

    private val readContactLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result->
        if (result) {
            readContacts()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList)

        viewBinding.contactsView.adapter = adapter

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            readContactLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            readContacts()
        }
    }

    fun readContacts() {
        // 查询联系人数据
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)?.apply {
            while (moveToNext()) {
                // 获取联系人姓名
                val displayName = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = getString(getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName \n$number")
                adapter.notifyDataSetChanged()
            }
            close()
        }
    }
}