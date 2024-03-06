package com.ttt.employeeapplication.Repository

import com.ttt.employeeapplication.Bean.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserRepository {
    @POST("user")
    fun addUser(@Body user: User?): Call<Void>

    @GET("user")
    fun getAllUser(): Call<List<User>>

    @GET("user/{id}")
    fun getUserById(@Path("id") Id: Int): Call<User>

    @GET("user/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

    @GET("user/login/{emailId}/{password}")
    fun getUserByEmailAndPassword(@Path("emailId") emailId: String,@Path("password") password:String): Call<String>


    @PUT("user/{id}")
    fun updateUser(@Path("id") id: Int, @Body updatedUser: User?): Call<User>

    @DELETE("user/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}