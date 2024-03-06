package com.ttt.employeeapplication.Service

import android.util.Log
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.Repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserService(private val userRepository:UserRepository) {
    suspend fun getAllUser(): List<User> {

        return suspendCoroutine { continuation ->
            val call: Call<List<User>> = userRepository.getAllUser()

            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val userList: List<User>? = response.body()
                        if (userList != null) {
                            continuation.resume(userList)
                        } else {
                            continuation.resumeWithException(NullPointerException("Response body is null"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getUserById(userId: Int): User {
        return suspendCoroutine { continuation ->
            val call: Call<User> = userRepository.getUserById(userId)

            Log.d("NetworkRequest", "Request URL: ${call.request().url()}")

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user: User? = response.body()
                        if (user != null) {
                            continuation.resume(user)
                        } else {
                            continuation.resumeWithException(NullPointerException("Response body is null"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getUserByEmail(userEmail: String): User {
        return suspendCoroutine { continuation ->
            val call: Call<User> = userRepository.getUserByEmail(userEmail)

            Log.d("NetworkRequest", "Request URL: ${call.request().url()}")

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user: User? = response.body()
                        if (user != null) {
                            continuation.resume(user)
                        } else {
                            continuation.resumeWithException(NullPointerException("Response body is null"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun addUser(user: User) {
        return suspendCoroutine { continuation ->
            val call: Call<Void> = userRepository.addUser(user)

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
    suspend fun deleteUser(userId: Int) {
        return suspendCoroutine { continuation ->
            val call: Call<Void> = userRepository.deleteUser(userId)

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

    suspend fun updateUser(userId: Int, updatedUser: User) {
        return suspendCoroutine { continuation ->
            val call: Call<User> = userRepository.updateUser(userId, updatedUser)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getUserByEmailAndPassword(emailId: String, password: String): String {
        return suspendCoroutine { continuation ->
            val call: Call<String> = userRepository.getUserByEmailAndPassword(emailId, password)

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    } else {
                        continuation.resumeWithException(Exception("Error Response: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}