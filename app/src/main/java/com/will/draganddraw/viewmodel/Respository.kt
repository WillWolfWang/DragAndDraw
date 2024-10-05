package com.will.draganddraw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Respository {

    // 模拟从服务器获取数据
    fun getUser(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        liveData.value = User(userId, userId, 0)
        return liveData
    }
}