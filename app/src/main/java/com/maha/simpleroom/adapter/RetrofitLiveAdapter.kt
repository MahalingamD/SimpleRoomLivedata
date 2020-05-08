package com.maha.simpleroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maha.simpleroom.R
import com.maha.simpleroom.activity.RetrofitLivedataActivity
import com.maha.simpleroom.activity.RoomLiveDataActivity
import com.maha.simpleroom.db.entity.User
import com.maha.simpleroom.model.ResponseUser
import com.maha.simpleroom.viewmodel.RetrofitLiveDataViewModel
import com.maha.simpleroom.viewmodel.RoomLiveDataViewModel

class RetrofitLiveAdapter(
    mainActivity: RetrofitLivedataActivity,
    var mUserList: ArrayList<ResponseUser>,
    var mViewmodel: RetrofitLiveDataViewModel
) : RecyclerView.Adapter<RetrofitLiveAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val aNameText: TextView = itemView.findViewById(R.id.name_txt)
        private val aCountryText: TextView = itemView.findViewById(R.id.country_txt)
        private val aMoneyText: TextView = itemView.findViewById(R.id.money_txt)
        private val aDeleteBut: TextView = itemView.findViewById(R.id.but_delete)
        private val aEditBut: TextView = itemView.findViewById(R.id.but_Edit)

        fun bindItems(position: Int) {
            val auser = mUserList[position]
            aNameText.text = auser.id
            aCountryText.text = auser.title
            aMoneyText.text = auser.body

            aDeleteBut.visibility=View.GONE
            aEditBut.visibility=View.GONE
        }
    }


    fun update(aUserList: ArrayList<ResponseUser>) {
        mUserList.addAll(aUserList)
        notifyDataSetChanged()
    }
}