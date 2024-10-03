package com.will.draganddraw.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.will.draganddraw.databinding.ActivityNewsBinding

class NewsActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}