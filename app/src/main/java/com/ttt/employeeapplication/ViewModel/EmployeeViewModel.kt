package com.ttt.employeeapplication.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.Repository.UserRepository
import com.ttt.employeeapplication.Service.UserService

class EmployeeViewModel(UserRepository: UserRepository) : ViewModel() {

    var userService:UserService

    var _alluser= mutableStateListOf<User>()
    var allUser: List<User> by mutableStateOf(_alluser)

    init {
        userService= UserService(UserRepository)
    }

    /////USER REPO
    suspend fun getAllUser(){
        allUser = userService.getAllUser()
    }

    suspend fun getUserById(userId: Int): User {
        return userService.getUserById(userId)
    }

    suspend fun getUserByEmail(email: String): User {
        return userService.getUserByEmail(email)
    }


    suspend fun addUser(user: User){
        return userService.addUser(user)
    }

    suspend fun deleteUser(userId: Int){
        return userService.deleteUser(userId)
    }

    suspend fun updateUser(userId: Int,newUser: User){
        return userService.updateUser(userId,newUser)
    }

    suspend fun getUserByEmailAndPassword(email: String,password:String): String {
        return userService.getUserByEmailAndPassword(email,password)
    }
///Employee

//    suspend fun getAllEmployees() {
//            allEmployees = employeeService.getAllEmployee()
//    }
//
//    suspend fun getEmployeeById(employeeId: Int): Employee {
//           return employeeService.getEmployeeById(employeeId)
//        }
//
//    suspend fun addEmployee(employee: Employee){
//        return employeeService.addEmployee(employee)
//    }
//
//    suspend fun deleteEmployee(employeeId: Int){
//        return employeeService.deleteEmployee(employeeId)
//    }
//
//    suspend fun updateEmployee(employeeId: Int,newEmployee: Employee){
//        return employeeService.updateEmployee(employeeId,newEmployee)
//    }

}