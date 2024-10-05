package com.will.draganddraw.roomtest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.will.draganddraw.viewmodel.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long

    @Query("select * from User")
    fun loadAllUser():List<User>
}