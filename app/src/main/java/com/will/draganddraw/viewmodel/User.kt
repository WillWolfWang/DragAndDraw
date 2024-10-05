package com.will.draganddraw.viewmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {

    // 主键是自动增长的
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}