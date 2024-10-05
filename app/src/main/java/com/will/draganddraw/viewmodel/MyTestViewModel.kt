package com.will.draganddraw.viewmodel

import androidx.lifecycle.ViewModel

/**
 * 如何 ViewModel 中传递参数
 *
 * 由于所有的 ViewModel 都是通过 ViewModelProvider 来获取的，没有任何地方可以
 * 向 ViewModel 的构造函数中传递参数
 *
 * 需要借助 ViewModelProvider.Factory 就可以实现了
 */
class MyTestViewModel(countReserved: Int): ViewModel() {

    var counter = countReserved
}