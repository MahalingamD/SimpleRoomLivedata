package com.maha.simpleroom.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maha.simpleroom.R
import com.maha.simpleroom.adapter.UserAdapter
import com.maha.simpleroom.db.database.UserDatabase
import com.maha.simpleroom.db.entity.User
import com.maha.simpleroom.viewmodel.RoomLiveDataViewModel
import kotlinx.android.synthetic.main.activity_room_live_data.*

class RoomLiveDataActivity : AppCompatActivity() {


    var mUserList = arrayListOf<User>()

    lateinit var mContext: Context

    var aCount = 0


    var mUserDataBase: UserDatabase? = null

    lateinit var mUserAdapter : UserAdapter

    lateinit var   mViewmodel :RoomLiveDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_live_data)

        mContext = this
        // configureGoogleSignIn()

        mViewmodel = ViewModelProvider(this).get(RoomLiveDataViewModel::class.java)

        setUpRecyclerView()

        mUserDataBase = UserDatabase.getDatabase(this)


        mViewmodel.getUserDetail()?.observe(this, Observer {
            // mUserList =
            mUserAdapter.update(it as ArrayList<User>)
        })


        clickListener()

    }

    private fun clickListener() {
        add_but.setOnClickListener {
            val aUser = User()
            aUser.name = "A $aCount"
            aUser.country = "India $aCount"
            aUser.money = "$aCount"
            aUser.usertype = "sample"

            mViewmodel.insertUserDetail(aUser)

            aCount++
        }
    }


    private fun setUpRecyclerView() {

        val myLayoutManager = LinearLayoutManager(mContext)
        user_recyclerview!!.layoutManager = myLayoutManager
        user_recyclerview!!.setHasFixedSize(true)

        mUserAdapter = UserAdapter(this, mUserList,mViewmodel )
        user_recyclerview!!.adapter = mUserAdapter
    }
}
