package com.will.draganddraw.filesave

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.will.draganddraw.databinding.ActivityFileSaveBinding
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class FileSaveActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityFileSaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFileSaveBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // 添加一个 回退按钮的监听
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })


    }

    override fun onStop() {
        super.onStop()
        Log.e("WillWolf", "onStop");
        val text = viewBinding.etInput.text.toString()
        save(text)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 一加手机不调用 onDestroy
        Log.e("WillWolf", "onDestroy");
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun save(inputText: String) {
        val output = openFileOutput("data", Context.MODE_PRIVATE)
        val write = BufferedWriter(OutputStreamWriter(output))
        write.use {
            it.write(inputText)
        }
    }
}