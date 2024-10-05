package com.will.draganddraw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
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
    private var _counter = MutableLiveData<Int>()
    // 对外暴露不可修改的 LiveData
    val counter: LiveData<Int>
        get() = _counter

    // 假设有一个 User 类的 LiveData
    // 但是我们只需要使用用户的姓名
    private val userLiveData = MutableLiveData<User>()
    /**
     * 可以使用 map 方法将一个类型的 LiveData 转成另一个类型的 LiveData
     *
     * 当 userLiveData 数据发生变化时，map 方法就监听到变化，并执行转换函数中的逻辑，
     * 然后再将转换之后的数据通知给 userName 的观察者
     *
      */
    val userName: LiveData<String> = userLiveData.map {user: User ->
        "${user.firstName} ${user.lastName}"
    }


    // 为什么有 init 方法，因为 kotlin 没有办法
    // 在主构造器中进行一些初始化操作，所以加了一个 init
    init {
        _counter.value = countReserved
    }

    fun plusOne() {
        thread {
            // 模拟耗时
            Thread.sleep(2000)
            val count = _counter.value ?: 0
            // 如果在子线程中，需要使用 postValue 方法
            _counter.postValue( count + 1)
//            counter.value =
        }
    }


//    fun getUser(userId: String): LiveData<User> {
//        return Respository.getUser(userId)
//    }
    /**
     * 如果在 activity 中直接使用 getUser（） 这个 LiveData 去观察，那么会出现问题
     * 因为 Respository 每次都是 new 一个新的 LiveData，
     * 此时需要使用 switchMap 方法
     *
     * 它的使用场景很固定，如果 ViewModel 中某个 LiveData 对象是调用另外
     * 的方法获取的，那么我们就可以借助 switchMap 方法，将这个 LiveData 对象
     * 转换成另外一个可观察的 LiveData
     */
    private val userIdLiveData = MutableLiveData<String>()

    val user: LiveData<User> = userIdLiveData.switchMap {userId ->
        Respository.getUser(userId)
    }
    fun getUser(userId: String) {
        userIdLiveData.value = userId
    }
    /**
     * 工作流程：外部调用 getUser(userId: String) 来获取用户数据时，并不会发起任何请求，
     * 只是将传入的 userId 值设置到 userIdLiveData 中，一旦 userIdLiveData 数据发生变化，
     * 那么 switchMap 就会执行，获取到用户数据后，将 返回的 LiveData 转换成一个可观察的
     * liveData，然后 Activity 就可以观察这个 LiveData 了
     */

}