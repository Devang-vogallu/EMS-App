package com.ttt.employeeapplication.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun EmployeeDetail(
    employeeId: Int,
    employeeViewModel: EmployeeViewModel,
    navigateBack: () -> Unit,
    navigateToEditEmployee: (Int) -> Unit,
    navigateToLoginScreen: () -> Unit
) {
    EmployeeDetailsContent(
        employeeId,
        employeeViewModel,
        navigateBack,
        navigateToEditEmployee,
        navigateToLoginScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmployeeDetailsContent(
    employeeId: Int,
    employeeViewModel: EmployeeViewModel,
    navigateBack: () -> Unit,
    navigateToEditDetail: (Int) -> Unit,
    navigateToLoginScreen: () -> Unit
) {
    val employee = remember(employeeId) { mutableStateOf<User?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var loggedIn by remember { mutableStateOf(true) }
    var role: String?
    var id: Int
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    loggedIn = sharedPreferences.getBoolean(LOGGED_IN_KEY, true)
    role = sharedPreferences.getString(ROLE, "ADMIN")
    id = sharedPreferences.getInt(ID, 1)


    LaunchedEffect(employeeId) {
        employee.value = withContext(Dispatchers.IO) {
            employeeViewModel.getUserById(employeeId)
        }
    }

    employee.value?.let { nonNullEmployee ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Employee Details") },
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
            println("AAA id in shared pref=$id  AND id in userBean=${nonNullEmployee.id}")
            Card(
                modifier = Modifier
                    .padding(10.dp, 70.dp, 10.dp, 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Black),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Employee Name
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Name")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Name: ${nonNullEmployee.employee.employeeName} (${nonNullEmployee.id})",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    //Employee Email
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Email: ${nonNullEmployee.emailId}",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    // Employee Salary
                    if (id == nonNullEmployee.id || role == "ADMIN") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = "Salary")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Salary: ${nonNullEmployee.employee.employeeSalary}",
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }

                    // Employee Department
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "Department")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Department: ${nonNullEmployee.employee.employeeDepartment}",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    // Employee Contact
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Contact")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Contact: ${nonNullEmployee.employee.employeeContact}",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    // Employee Location
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Location")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Location: ${nonNullEmployee.employee.employeeLocation}",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (id == nonNullEmployee.id || role == "ADMIN") {
                            OutlinedButton(
                                border = BorderStroke(5.dp, Color.Black),
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(100.dp),
                                onClick = {
                                    navigateToEditDetail(nonNullEmployee.employee.employeeId)
                                }
                            ) {
                                Text(
                                    text = "Update",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        if (role == "ADMIN") {
                            OutlinedButton(
                                border = BorderStroke(5.dp, Color.Black),
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(100.dp),
                                onClick = {
                                    showDialog.value = true
                                }
                            ) {
                                Text(
                                    text = "Delete",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Delete Confirmation Dialog
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this user?") },
                confirmButton = {
                    OutlinedButton(
                        border = BorderStroke(5.dp, Color.Black),
                        onClick = {
                            if (nonNullEmployee.role!="ADMIN") {
                                employeeViewModel.viewModelScope.launch {
                                    employeeViewModel.deleteUser(nonNullEmployee.employee.employeeId)
                                    showDialog.value = false
                                    navigateBack()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Admin Cannot be deleted",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    ) {
                        Text(
                            text = "Delete",
                            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                        )
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        border = BorderStroke(5.dp, Color.Black),
                        onClick = { showDialog.value = false }
                    ) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                        )
                    }
                }
            )
        }
    }
}


