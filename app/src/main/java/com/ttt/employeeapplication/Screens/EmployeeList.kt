package com.ttt.employeeapplication.Screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.runtime.DisposableEffect
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
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeList(
    employeeViewModel: EmployeeViewModel,
    navigateToEmployeeDetails: (Int) -> Unit,
    navigateToAddEmployee: () -> Unit,
    navigateToLoginScreen: () -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var loggedIn by remember { mutableStateOf(true) }
    loggedIn = sharedPreferences.getBoolean(LOGGED_IN_KEY, true)
    val showDialog = remember { mutableStateOf(false) }
    val user: List<User> = employeeViewModel.allUser
    var role: String?
    var name: String?
    role = sharedPreferences.getString(ROLE, "ADMIN")
    name = sharedPreferences.getString(NAME, "")
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher


    DisposableEffect(Unit) {
        val onBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as? Activity)?.finish()
            }
        }

        // Add the callback to the back press dispatcher
        onBackPressedDispatcher?.addCallback(onBackCallback)

        onDispose {
            onBackCallback.remove()
        }
    }

    LaunchedEffect(employeeViewModel) {
        employeeViewModel.getAllUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Employee Details")
                        Text("Welcome: ${name}", style = TextStyle(fontSize = 12.sp))
                    }
                },
                actions = {
                    // Check if the user has the required role to show the "Add Employee" icon
                    if (role == "ADMIN") {
                        IconButton(onClick = { navigateToAddEmployee() }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Employee")
                        }
                    }

                    // Logout icon
                    IconButton(onClick = { showDialog.value = true})
                     {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Exit/Logout")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 70.dp, 0.dp, 0.dp)
        ) {
            items(user) { user ->
                Employee(user = user) {
                    navigateToEmployeeDetails(user.employee.employeeId)
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to Logout?") },
            confirmButton = {
                OutlinedButton(
                    border = BorderStroke(5.dp, Color.Black),
                    onClick = {
                        loggedIn = false
                        sharedPreferences.edit().putBoolean(LOGGED_IN_KEY, loggedIn).apply()
                        sharedPreferences.edit().putString(NAME, "").apply()
                        sharedPreferences.edit().putString(ROLE, "").apply()
                        sharedPreferences.edit().putString(EMAIL, "").apply()
                        sharedPreferences.edit().putInt(ID, 0).apply()
                        navigateToLoginScreen()
                    }
                ) {
                    Text(
                        text = "Logout",
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

@Composable
fun Employee(user: User, onClick: () -> Unit) {
    val isVisible by remember { mutableStateOf(true) }
    val id: Int
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    id = sharedPreferences.getInt(ID, 1)


    val customColor = if (id == user.id) {
        // If condition is met, use one color
        Color(144, 238, 144)
    } else {
        // If condition is not met, use another color
        Color(211, 211, 211)
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(onClick = onClick)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(customColor)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Employee details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Account Icon",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(0.dp),
                        tint = Color.Black
                    )

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "(${user.employee.employeeId}) - ${user.employee.employeeName}",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = user.employee.employeeDepartment,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        )
                    }
                }
            }
        }
    }
}
