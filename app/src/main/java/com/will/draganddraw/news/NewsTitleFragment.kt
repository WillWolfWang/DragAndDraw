package com.will.draganddraw.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cursoradapter.widget.SimpleCursorAdapter.ViewBinder
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.will.draganddraw.R
import com.will.draganddraw.databinding.NewsItemBinding
import com.will.draganddraw.databinding.NewsTitleFragBinding

class NewsTitleFragment: Fragment() {
    private lateinit var viewBinding : NewsTitleFragBinding
    private var isTwoPane = false
    private lateinit var newsContentFragment: NewsContentFragment

    // 注册 activity 的声明周期观察者，因为 onActivityCreate 生命周期废弃了
    private var lifecycleObserver = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_CREATE) {
                // 观察到 activity onCreate 事件
                Log.e("WillWolf", "activity-->" + activity)
                isTwoPane = (activity as NewsActivity).findViewById<View>(R.id.newsContentLayout) != null
                newsContentFragment = activity?.supportFragmentManager?.findFragmentById(R.id.newsContentFrag) as NewsContentFragment
                Log.e("WillWolf", "isTwoPane-->" + isTwoPane)
                source.lifecycle.removeObserver(this)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(lifecycleObserver)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = NewsTitleFragBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        viewBinding.newsTitleRecyclerView.layoutManager = layoutManager
        var adapter = NewsAdapter(getNews())
        viewBinding.newsTitleRecyclerView.adapter = adapter
    }

    private fun getNews(): List<News> {
        val newList = ArrayList<News>()
        for (i in 1..50) {
            var news = News("This is news title $i", getRandomLengthString("This is news content$i"))
            newList.add(news)
        }
        return newList
    }

    private fun getRandomLengthString(str: String): String {
        val n = (1..20).random()

        return str * n
    }

    // 扩展函数和运算符重载
    operator fun String.times(n: Int) : String {
        val builder = StringBuilder()
        repeat(n) {
            builder.append(this)
        }
        return builder.toString()
    }

    inner class NewsAdapter(val newsList: List<News>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val viewBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return NewsViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return newsList.size
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            var news = newsList.get(position)
            holder.newsTitle.text = news.title
        }

        inner class NewsViewHolder(viewbinding: ViewBinding): RecyclerView.ViewHolder(viewbinding.root) {
            var newsTitle: TextView = viewbinding.root as TextView
            init {
                newsTitle.setOnClickListener {
                    var news = newsList.get(bindingAdapterPosition)
                    if (isTwoPane) {
                        newsContentFragment.refresh(news.title, news.content)
                    } else {
                        NewsContentActivity.actionStart(requireContext(), news.title, news.content)
                    }
                }
            }
        }

    }
}