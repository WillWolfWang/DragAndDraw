package com.will.draganddraw.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.will.draganddraw.databinding.ActivityMyTestBinding

class MyTestActivity: AppCompatActivity() {
    private lateinit var viewModel: MyTestViewModel
    private lateinit var viewbinding: ActivityMyTestBinding
    private lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMyTestBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        /**
         * 绝对不可以直接创建 ViewModel，而是通过 ViewModelProvider 去获取 你的ViewModel 实例
         *
         * 这是因为 ViewModel 有其独立的生命周期，并且其生命周期长于 Activity，
         * 如果直接 new 的方式创建实例，那么每次 onCreate 就会创建一个新的实例
         *
         * 需要传值，必须使用 Factory
         */
        viewModel = ViewModelProvider(this, MyTestViewModelFactory(countReserved)).get(MyTestViewModel::class.java)

        viewbinding.plusOneBtn.setOnClickListener {
            viewModel.counter++
            refreshCounter()
        }

        refreshCounter()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter)
        }
    }

    private fun refreshCounter() {
        viewbinding.infoText.text = viewModel.counter.toString()
    }

}