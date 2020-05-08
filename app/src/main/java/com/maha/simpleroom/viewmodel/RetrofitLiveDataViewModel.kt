package com.maha.simpleroom.viewmodel


import android.app.Application
import android.app.Dialog
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ers.tkenterprise.apiUtils.ApiClient
import com.maha.simpleroom.R
import com.maha.simpleroom.model.ResponseUser
import com.maha.simpleroom.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitLiveDataViewModel( application: Application) : AndroidViewModel(application) {

    var aMutableUserList: MutableLiveData<List<ResponseUser>>? = null

    private lateinit var mProgressDialog: ProgressDialog

    init {
        aMutableUserList= MutableLiveData()
        mProgressDialog= ProgressDialog(application)
    }

   fun loadData(aCount:Int){

      // mProgressDialog.setMessage("loading")
      // mProgressDialog.setCancelable(false)
//       mProgressDialog.show()

       ApiClient.getService().getUserDetails(aCount).enqueue(object : Callback<List<ResponseUser>> {

           override fun onFailure(call: Call<List<ResponseUser>>, t: Throwable) {
             //  mProgressDialog.dismiss()
               Log.e("mess",t.message)
           }

           override fun onResponse(call: Call<List<ResponseUser>>, response: Response<List<ResponseUser>>) {
              // mProgressDialog.dismiss()
               if(response.isSuccessful&&response.code()==200){
                  aMutableUserList!!.value=response.body()
               }
           }
       })
   }


    fun getuserdetail():LiveData<List<ResponseUser>>{
        return this.aMutableUserList!!
    }




}