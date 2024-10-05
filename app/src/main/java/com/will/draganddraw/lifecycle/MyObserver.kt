package com.will.draganddraw.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent


/**
 * 可以感知 activity 生命周期的类
 * ，继承了 LifecycleObserver 只是一个空接口
 *
 * 需要借助额外的注解功能才能实现
 */
class MyObserver: DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.e("WillWolf", "activityStart-->")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.e("WillWolf", "activityStop-->")
    }

    // 这两个方法都是废弃的了
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun activityStart() {
//        Log.e("WillWolf", "activityStart-->")
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun activityStop() {
//        Log.e("WillWolf", "activityStop-->")
//    }
}