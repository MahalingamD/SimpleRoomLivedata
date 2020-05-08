package com.maha.simpleroom.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maha.simpleroom.R
import com.maha.simpleroom.adapter.RetrofitLiveAdapter
import com.maha.simpleroom.adapter.UserAdapter
import com.maha.simpleroom.db.database.UserDatabase
import com.maha.simpleroom.db.entity.User
import com.maha.simpleroom.model.ResponseUser
import com.maha.simpleroom.viewmodel.RetrofitLiveDataViewModel
import com.maha.simpleroom.viewmodel.RoomLiveDataViewModel
import kotlinx.android.synthetic.main.activity_retrofit_livedata.*
import kotlinx.android.synthetic.main.activity_room_live_data.*

class RetrofitLivedataActivity : AppCompatActivity() {

    var mUserList = arrayListOf<ResponseUser>()

    lateinit var mContext: Context

    var aCount = 1

    var mUserDataBase: UserDatabase? = null

    lateinit var mUserAdapter : RetrofitLiveAdapter

    lateinit var   mViewmodel :RetrofitLiveDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_livedata)


        mContext = this
        // configureGoogleSignIn()

        mViewmodel = ViewModelProvider(this).get(RetrofitLiveDataViewModel::class.java)

        setUpRecyclerView()

        mUserDataBase = UserDatabase.getDatabase(this)


        mViewmodel.getuserdetail().observe(this, Observer {
            // mUserList =
            mUserAdapter.update(it as ArrayList<ResponseUser>)
        })

        clickListener()
    }


    private fun setUpRecyclerView() {

        val myLayoutManager = LinearLayoutManager(mContext)
        retro_recyclerview!!.layoutManager = myLayoutManager
        retro_recyclerview!!.setHasFixedSize(true)

        mUserAdapter = RetrofitLiveAdapter(this, mUserList,mViewmodel )
        retro_recyclerview!!.adapter = mUserAdapter
    }


    private fun clickListener() {
        load_but.setOnClickListener {

            mViewmodel.loadData(aCount)
            aCount++
        }
    }
}
