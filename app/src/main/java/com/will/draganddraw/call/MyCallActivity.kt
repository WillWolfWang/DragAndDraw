package com.will.draganddraw.call

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.will.draganddraw.databinding.ActivityCallBinding

class MyCallActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityCallBinding

    private val callPhoneLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result->
        if (result) {
            callPhoto()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnCall.setOnClickListener {

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                callPhoneLauncher.launch(Manifest.permission.CALL_PHONE)
            } else {
                callPhoto()
            }
        }
    }

    private fun callPhoto() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:10086")
        startActivity(intent)
    }
}