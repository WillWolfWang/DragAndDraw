package com.will.draganddraw.offline

import android.content.Intent
import android.os.Bundle
import com.will.draganddraw.databinding.ActivityShowBinding

class ShowActivity: BaseActivity() {
    private lateinit var viewBinding: ActivityShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnOffline.setOnClickListener {
            val intent = Intent("com.will.offline")
            sendBroadcast(intent)
        }
    }
}