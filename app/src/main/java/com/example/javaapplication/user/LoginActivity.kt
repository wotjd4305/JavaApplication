package com.example.javaapplication.user

import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import com.example.javaapplication.MainActivity
import com.example.javaapplication.R
import com.example.javaapplication.db.SharedPreferenceController
import com.example.javaapplication.network.ApplicationController
import com.example.javaapplication.network.NetworkService
import com.example.user.alcohol_measurement.PostLogInResponse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setOnBtnClickListener()

        /*
        if (SharedPreferenceController.getAuthorization(this).isNotEmpty()){
            startActivity<MainActivity>()
        }*/
    }

    private fun setOnBtnClickListener(){
        btn_main_act_log_in.setOnClickListener {
            getLoginResponse()
        }

        btn_main_act_sign_up.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun getLoginResponse(){
        if (et_main_act_email.text.toString().isNotEmpty() && et_main_act_pw.text.toString().isNotEmpty()){
            val input_email = et_main_act_email.text.toString()
            val input_pw = et_main_act_pw.text.toString()
            val jsonObject : JSONObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("password", input_pw)
            val gsonObject : JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)
            postLogInResponse.enqueue(object : Callback<PostLogInResponse>{
                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    if (response.isSuccessful){
                        val token = response.body()!!.data.token
                        //저번 시간에 배웠던 SharedPreference에 토큰을 저장! 왜냐하면 토큰이 필요한 통신에 사용하기 위해서!!
                        SharedPreferenceController.setAuthorization(this@LoginActivity, token)
                        toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            })
        }
    }
}