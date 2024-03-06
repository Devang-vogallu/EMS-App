package com.ttt.employeeapplication.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.viewModelScope
import com.ttt.employeeapplication.Bean.Employee
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditEmployee(
    employeeId: Int,
    employeeViewModel: EmployeeViewModel,
    navigateBack: () -> Unit,
    navigateToList: () -> Unit
)
{
    EditDetailContent(employeeId,employeeViewModel,navigateBack,navigateToList)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun EditDetailContent(
    employeeId: Int,
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
    var role:String?
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    role = sharedPreferences.getString(ROLE,"ADMIN")

    val Employee = remember(employeeId) { mutableStateOf<User?>(null) }

    LaunchedEffect(employeeId) {
        Employee.value = withContext(Dispatchers.IO) {
            employeeViewModel.getUserById(employeeId)
        }
    }

    Employee.value?.let { nonNullEmployee ->
        emailId=nonNullEmployee.emailId
        password=nonNullEmployee.password//.encodeToByteArray().toString()
        name= nonNullEmployee.employee.employeeName
        salary= nonNullEmployee.employee.employeeSalary
        department=nonNullEmployee.employee.employeeDepartment
        contact=nonNullEmployee.employee.employeeContact
        location=nonNullEmployee.employee.employeeLocation

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Update Employee") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigateBack()
                        }) {
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
                //Email
                TextField(
                    value = emailId,
                    onValueChange = { emailId = it },
                    label = { Text("Email Id") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Id")
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
                        Icon(Icons.Default.Lock, contentDescription = "Password")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                if(role=="ADMIN") {
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
                }

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
                if(role=="ADMIN")
                {
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
                }

                // Update Button
                OutlinedButton(
                    border = BorderStroke(5.dp, Color.Black),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if(name!="" && salary!=null &&department!="" &&location!="" &&contact!="")
                        {
                            val id= nonNullEmployee.employee.employeeId

                            val newUser = User(
                                id=id,
                                role ="User",
                                emailId=emailId,
                                password=password,
                          Employee(
                                employeeId=id,
                                employeeName = name,
                                employeeSalary = salary,
                                employeeDepartment = department,
                                employeeContact=contact,
                                employeeLocation = location
                                    )
                            )
                            employeeViewModel.viewModelScope.launch {
                                employeeViewModel.updateUser(id,newUser)
                            }
                            if(role=="ADMIN"){
                                navigateToList()
                            }
                            else{
                                navigateBack()
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Any Feilds should not be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text(text = "Update", style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black))
                }
            }
        }
    }
}
