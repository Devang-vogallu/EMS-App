package com.ttt.employeeapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.GsonBuilder
import com.ttt.employeeapplication.Repository.UserRepository
import com.ttt.employeeapplication.Screens.AddEmployee
import com.ttt.employeeapplication.Screens.EditEmployee
import com.ttt.employeeapplication.Screens.EmployeeDetail
import com.ttt.employeeapplication.Screens.EmployeeList
import com.ttt.employeeapplication.Screens.LOGGED_IN_KEY
import com.ttt.employeeapplication.Screens.LoginScreen
import com.ttt.employeeapplication.Screens.PREFS_NAME
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://172.27.104.157:8080/"
    private var retrofit: Retrofit? = null

    private val gson = GsonBuilder().setLenient().create()
    val userRepository: UserRepository
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!.create(UserRepository::class.java)
        }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val userRepository = RetrofitInstance.userRepository
        val employeeViewModel: EmployeeViewModel by viewModels() {
            EmployeeViewModelFactory(userRepository)
        }

        super.onCreate(savedInstanceState)

        setContent {
            AppContent(employeeViewModel)
        }
    }
}
@Composable
fun AppContent(employeeViewModel: EmployeeViewModel) {
    val navController = rememberNavController()
    var loggedIn by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    loggedIn = sharedPreferences.getBoolean(LOGGED_IN_KEY, true)

    val startDestination = if (loggedIn) {
        "EmployeeList"
    } else {
        "UserLogin"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("UserLogin") {
            LoginScreen(
                employeeViewModel = employeeViewModel,
                navigateToEmployeeList = {
                    navController.navigate("EmployeeList")
                }
            )
        }
        composable("EmployeeList") {
            EmployeeList(
                employeeViewModel = employeeViewModel,
                navigateToEmployeeDetails = { employeeId ->
                    navController.navigate("EmployeeDetails/$employeeId")
                },
                navigateToAddEmployee = {
                    navController.navigate("addEmployee")
                },
                navigateToLoginScreen = {
                    navController.navigate("UserLogin")
                }
            )
        }
        composable(
            "EmployeeUpdate/{employeeId}",
            arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getInt("employeeId")
                ?: throw IllegalArgumentException("Task ID missing")
            EditEmployee(
                employeeId = employeeId,
                employeeViewModel = employeeViewModel,
                navigateBack = { navController.popBackStack() },
                navigateToList = {
                    navController.navigate("EmployeeList")
                }
            )
        }
        composable(
            "EmployeeDetails/{employeeId}",
            arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getInt("employeeId")
                ?: throw IllegalArgumentException("Task ID missing")
            EmployeeDetail(
                employeeId = employeeId,
                employeeViewModel = employeeViewModel,
                navigateBack = { navController.popBackStack() },
                navigateToEditEmployee = {
                    navController.navigate("EmployeeUpdate/$employeeId")
                },
                navigateToLoginScreen = {
                    navController.navigate("UserLogin")
                }
            )
        }
        composable("addEmployee") {
            AddEmployee(
                employeeViewModel = employeeViewModel,
                navigateBack = { navController.popBackStack() },
                navigateToList = {
                    navController.navigate("EmployeeList")
                }
            )
        }
    }
}
