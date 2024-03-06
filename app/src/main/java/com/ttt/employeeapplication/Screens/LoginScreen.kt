package com.ttt.employeeapplication.Screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.ttt.employeeapplication.Bean.User
import com.ttt.employeeapplication.R
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel
import kotlinx.coroutines.launch


const val PREFS_NAME = "MyPrefs"
const val LOGGED_IN_KEY = "loggedIn"
const val EMAIL="email"
const val ROLE ="role"
const val ID="id"
const val NAME="name"

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    employeeViewModel: EmployeeViewModel,
    navigateToEmployeeList: () -> Unit,
) {
    var loggedIn by remember { mutableStateOf(false) }
    var user: User
    var email by rememberSaveable { mutableStateOf("") }
    var emailId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var status: String
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    loggedIn = sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    email = sharedPreferences.getString(EMAIL, "")!!
    sharedPreferences.getString(ROLE, "")!!
    sharedPreferences.getInt(ID,1)
    sharedPreferences.getString(NAME, "")!!



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


    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.White, // Color when the field is focused
        unfocusedBorderColor = Color.White, // Color when the field is unfocused
        cursorColor = Color.White // Color of the cursor
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp) // Adjust the size as needed
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Email ID TextField
        OutlinedTextField(
            colors = textFieldColors,
            value = emailId,
            onValueChange = { emailId = it },
            label = { Text("Email ID", style = TextStyle(color = Color.White)) },
            textStyle = TextStyle(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        OutlinedTextField(
            colors = textFieldColors,
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = TextStyle(color = Color.White)) },
            textStyle = TextStyle(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Login Button
        OutlinedButton(
            border = BorderStroke(5.dp, Color.White),
            onClick = {
                employeeViewModel.viewModelScope.launch {
                    status = employeeViewModel.getUserByEmailAndPassword(emailId, password)
                    if (status == "Loggin Successfull") {
                        user = employeeViewModel.getUserByEmail(emailId)
                        sharedPreferences.edit().putString(ROLE, user.role).apply()
                        sharedPreferences.edit().putString(NAME, user.employee.employeeName).apply()
                        sharedPreferences.edit().putInt(ID, user.id).apply()
                        navigateToEmployeeList()
                        loggedIn = true
                        sharedPreferences.edit().putString(EMAIL, emailId).apply()
                        sharedPreferences.edit().putBoolean(LOGGED_IN_KEY, loggedIn).apply()
                    } else {
                        Toast.makeText(context, "${status}", Toast.LENGTH_LONG).show()
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Login", style = TextStyle(color = Color.White))
        }
    }
}
