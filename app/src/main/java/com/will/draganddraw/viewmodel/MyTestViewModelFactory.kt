package com.will.draganddraw.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyTestViewModelFactory(private val countReserved: Int): ViewModelProvider.Factory {

    /**
     * 在 create 方法中，将参数传递到 我们的ViewModel 中，
     * 为什么这里可以创建 ViewModel，因为这个 create() 方法与 Activity 的生命周期无关，所以
     * 不会产生重复创建的问题
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyTestViewModel(countReserved) as T
    }
}