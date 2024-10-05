package com.will.draganddraw.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

/**
 * 如何 ViewModel 中传递参数
 *
 * 由于所有的 ViewModel 都是通过 ViewModelProvider 来获取的，没有任何地方可以
 * 向 ViewModel 的构造函数中传递参数
 *
 * 需要借助 ViewModelProvider.Factory 就可以实现了
 */
class MyTestViewModel(countReserved: Int): ViewModel() {

    /**
     * 为什么用 LiveData，因为有些数据可能是耗时操作才能获取
     * 所以，如果直接通过 get 方法获取这些数据，这些数据还未必是
     * 最新的数据
     *
     * 使用 LiveData，可以通过订阅的方式，当 LiveData 数据更新
     * 然后通知订阅者
     */
    var counter = MutableLiveData<Int>()
    // 为什么有 init 方法，因为 kotlin 没有办法
    // 在主构造器中进行一些初始化操作，所以加了一个 init
    init {
        counter.value = countReserved
    }

    fun plusOne() {
        thread {
            // 模拟耗时
            Thread.sleep(2000)
            val count = counter.value ?: 0
            // 如果在子线程中，需要使用 postValue 方法
            counter.postValue( count + 1)
//            counter.value =
        }

    }
}