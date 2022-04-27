package com.example.m_expenses.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    //http://gillwindallapp1.appspot.com/madservlet
    @FormUrlEncoded
    @POST("/madservlet")
    fun upload(@Field("jsonpayload") request: String): Call<UploadResponse>

}