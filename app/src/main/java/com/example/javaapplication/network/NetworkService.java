package com.example.javaapplication.network;

import com.google.gson.JsonObject
import com.sopt_nyh.retrofit2_example.get.GetBoardListResponse
import com.sopt_nyh.retrofit2_example.get.GetDetailedBoardResonse
import com.sopt_nyh.retrofit2_example.post.PostLogInResponse
import com.sopt_nyh.retrofit2_example.post.PostSignUpResponse
import com.sopt_nyh.retrofit2_example.post.PostWriteBoardResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //회원가입
    @POST("/users")
    fun postSignUpResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<PostSignUpResponse>

    //로그인
    @POST("/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<PostLogInResponse>

    //게시판 글쓰기
    @Multipart
    @POST("/contents")
    fun postWriteBoardResponse(
            @Header("Authorization") token : String,
            @Part("title") title : RequestBody,
            @Part("contents") contents : RequestBody,
            @Part photo: MultipartBody.Part?
    ) : Call<PostWriteBoardResponse>


    //모든 게시판 보기
    @GET("/contents")
    fun getBoardListResponse(
            @Header("Content-Type") content_type : String,
            @Query("offset") offset : Int,
            @Query("limit") limit : Int
    ) : Call<GetBoardListResponse>

    //게시물 상세 보기
    @GET("/contents/{contentIdx}")
    fun getDetailedBoardResponse(
            @Header("Content-Type") content_type : String,
            @Header("Authorization") token : String,
            @Path("contentIdx") contentIdx : Int
    ) : Call<GetDetailedBoardResponse>



}