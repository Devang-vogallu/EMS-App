package com.ttt.employeeapplication.Service

import android.util.Log
import com.ttt.employeeapplication.Repository.EmployeeRepository
import com.ttt.employeeapplication.Bean.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EmployeeService(private val employeeRepository: EmployeeRepository) {

    suspend fun getAllEmployee(): List<Employee> {

        return suspendCoroutine { continuation ->
            val call: Call<List<Employee>> = employeeRepository.getAllEmployees()

            call.enqueue(object : Callback<List<Employee>> {
                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {
                    if (response.isSuccessful) {
                        val employeeList: List<Employee>? = response.body()
                        if (employeeList != null) {
                            continuation.resume(employeeList)
                        } else {
                            continuation.resumeWithException(NullPointerException("Response body is null"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getEmployeeById(employeeId: Int): Employee {
        return suspendCoroutine { continuation ->
            val call: Call<Employee> = employeeRepository.getEmployeeById(employeeId)

            Log.d("NetworkRequest", "Request URL: ${call.request().url()}")

            call.enqueue(object : Callback<Employee> {
                override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                    if (response.isSuccessful) {
                        val employee: Employee? = response.body()
                        if (employee != null) {
                            continuation.resume(employee)
                        } else {
                            continuation.resumeWithException(NullPointerException("Response body is null"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Employee>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

        suspend fun addEmployee(employee: Employee) {
            return suspendCoroutine { continuation ->
                val call: Call<Void> = employeeRepository.addEmployee(employee)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }
        }

    suspend fun deleteEmployee(employeeId: Int) {
        return suspendCoroutine { continuation ->
            val call: Call<Void> = employeeRepository.deleteEmployee(employeeId)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun updateEmployee(employeeId: Int, updatedEmployee: Employee) {
        return suspendCoroutine { continuation ->
            val call: Call<Employee> = employeeRepository.updateEmployee(employeeId, updatedEmployee)

            call.enqueue(object : Callback<Employee> {
                override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                    if (response.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Employee>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}


