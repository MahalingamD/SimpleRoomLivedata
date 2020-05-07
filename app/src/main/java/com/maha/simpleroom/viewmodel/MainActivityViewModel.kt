package com.maha.simpleroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.maha.simpleroom.db.database.UserDatabase
import com.maha.simpleroom.db.entity.User

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    var aMutableUserList: LiveData<List<User>>? = null

    var mUserDataBase: UserDatabase? = null

    init {
        mUserDataBase = UserDatabase.getDatabase(application)
    }

    fun getUserDetail(): LiveData<List<User>>? {

        val aUserList = mUserDataBase!!.userDao().getUserList()

        return aUserList

    }

    fun insertUserDetail(aUser: User) {
        mUserDataBase!!.userDao().insert(aUser)
    }

    fun deleteUserDetail(aUser: User) {
        mUserDataBase!!.userDao().delete(aUser)
    }


}