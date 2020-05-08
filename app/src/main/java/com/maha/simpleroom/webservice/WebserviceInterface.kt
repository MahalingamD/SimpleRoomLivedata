package com.maha.simpleroom.webservice



import com.google.gson.JsonObject
import com.maha.simpleroom.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface WebserviceInterface {


    @Headers("Content-Type: application/json")
    @GET("posts")
    fun getUserDetails(@Query("userId") aUserID: Int): Call<List<ResponseUser>>



}