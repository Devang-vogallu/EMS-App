package com.ttt.employeeapplication.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.ttt.employeeapplication.Bean.Employee
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployee(
    employeeViewModel: EmployeeViewModel,
    navigateBack: () -> Unit,
    navigateToList: () -> Unit
) {
    var emailId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var salary by rememberSaveable { mutableStateOf(0) }
    var department by rememberSaveable { mutableStateOf("") }
    var contact by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Employee") },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 60.dp, 16.dp, 16.dp)
                .fillMaxWidth()
        ) {
            // Employee Name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Employee Name") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Employee Name")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            //emailId
            TextField(
                value = emailId,
                onValueChange = { emailId = it },
                label = { Text("Email ID") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Employee Email")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            //password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Employee Password")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Employee Salary
            TextField(
                value = salary.toString(),
                onValueChange = { salary = it.toIntOrNull() ?: 0 },
                label = { Text("Salary") },
                leadingIcon = {
                    Icon(Icons.Default.Favorite, contentDescription = "Salary")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Employee Department
            TextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Department") },
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "Department")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Employee Contact
            TextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Contact") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = "Contact")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Employee Location
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // Add Employee Button
            OutlinedButton(
                border = BorderStroke(5.dp,Black),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (emailId.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && salary != null && department.isNotEmpty() &&
                        location.isNotEmpty() && contact.isNotEmpty()
                    ) {

                        val newEmployee = User(
                            id = 0,
                            role="USER",
                            emailId=emailId,
                            password = password,
                            employee = Employee(
                                employeeId = 0,
                                employeeName = name,
                                employeeSalary = salary,
                                employeeDepartment = department,
                                employeeLocation = location,
                                employeeContact = contact
                            )
                        )
                        employeeViewModel.viewModelScope.launch {
                            employeeViewModel.addUser(newEmployee)
                        }
                        navigateToList()
                    } else {
                        Toast.makeText(context, "No Fields Should be Empty!", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = "Add Employee", style = TextStyle(fontWeight = FontWeight.Bold, color = Black))
            }
        }
    }
}
