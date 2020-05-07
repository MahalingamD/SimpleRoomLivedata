package com.maha.simpleroom.db.dao

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.room.*
import com.maha.simpleroom.db.entity.User

@Dao
interface userDao {

    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    fun insert(aUser:User)

    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    fun insert(aUserList:List<User>)

    @Delete()
    fun delete(aUser: User)

    @Update()
    fun update(aUser: User)


    @Query("SELECT * FROM user")
    fun getUserList() : LiveData<List<User>>


    @Query("SELECT * FROM user")
    fun getUserxurrentList() : List<User>
}