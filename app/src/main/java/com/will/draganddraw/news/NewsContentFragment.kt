package com.will.draganddraw.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.will.draganddraw.R
import com.will.draganddraw.databinding.NewsContentFragBinding

class NewsContentFragment: Fragment() {
    private lateinit var viewBinding: NewsContentFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.news_content_frag, container, false)
        viewBinding = NewsContentFragBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    fun refresh(title: String, content: String) {
        viewBinding.contentLayout.visibility = View.VISIBLE
        viewBinding.newsTitle.text = title
        viewBinding.newsContent.text = content
    }
}