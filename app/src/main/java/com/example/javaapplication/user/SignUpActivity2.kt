package com.example.javaapplication.User
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import com.example.javaapplication.network.ApplicationController
import com.example.javaapplication.network.NetworkService
import com.example.user.alcohol_measurement.PostSignUpResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupform);

        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        btn_sign_up_act_complete.setOnClickListener {
            getSignUpResponse()
        }
        btn_sign_up_act_close.setOnClickListener {
            finish()
        }
    }

    private fun getSignUpResponse() {
        //EditText에 있는 값 받기
        val input_name: String = et_sign_up_act_name.text.toString()
        val input_pw: String = et_sign_up_act_pw.text.toString()
        val input_email: String = et_sign_up_act_email.text.toString()
        val input_part: String = et_sign_up_act_part.text.toString()
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("name", input_name)
        jsonObject.put("email", input_email)
        jsonObject.put("password", input_pw)
        jsonObject.put("part", input_part)
        //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        //통신 시작
        val postSignUpResponse: Call<PostSignUpResponse> =
                networkService.postSignUpResponse("application/json", gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostSignUpResponse> {
            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }
            override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                if (response.isSuccessful){
                    var message : String = response.body()!!.message
                    toast(message)
                    finish()
                }
            }
        })
    }
}