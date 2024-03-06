package com.ttt.employeeapplication.Repository

import com.ttt.employeeapplication.Bean.Employee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface EmployeeRepository {
    @POST("employee")
    fun addEmployee(@Body employee: Employee?): Call<Void>

    @GET("employee")
    fun getAllEmployees(): Call<List<Employee>>

    @GET("employee/{id}")
    fun getEmployeeById(@Path("id") Id: Int): Call<Employee>

    @PUT("employee/{id}")
    fun updateEmployee(@Path("id") id: Int, @Body updatedEmployee: Employee?): Call<Employee>

    @DELETE("employee/{id}")
    fun deleteEmployee(@Path("id") id: Int): Call<Void>
}

//    @get:GET("/employee/")
//    val allEmployees: Call<List<Employee?>?>?