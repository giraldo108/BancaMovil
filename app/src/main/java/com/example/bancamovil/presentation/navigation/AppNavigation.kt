package com.example.bancamovil.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bancamovil.presentation.login.LoginView
import com.example.bancamovil.presentation.register.RegisterView
import com.example.bancamovil.presentation.transfer.TransferView


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginView(navController = navController)
        }
        composable("register") {
            RegisterView(navController = navController)
        }
        composable("home/{documentNumber}") { backStackEntry ->
            val documentNumber = backStackEntry.arguments?.getString("documentNumber") ?: ""
            // HomeView(navController = navController, documentNumber = documentNumber)
        }
        composable("transactions/{documento}") { backStackEntry ->
            val documento = backStackEntry.arguments?.getString("documento") ?: ""
            TransferView(currentUser = documento)
        }
    }
}