package com.example.bancamovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bancamovil.presentation.home.HomeView
import com.example.bancamovil.presentation.login.LoginView
import com.example.bancamovil.presentation.profile.ProfileView
import com.example.bancamovil.presentation.register.RegisterView
import com.example.bancamovil.presentation.transfer.TransferView
import com.example.bancamovil.ui.theme.BancaMovilTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BancaMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                        composable("home/{documento}") { backStackEntry ->
                            val documento = backStackEntry.arguments?.getString("documento") ?: ""
                            HomeView(navController = navController, documento = documento)
                        }
                        composable("transactions/{documento}") { backStackEntry ->
                            val documento = backStackEntry.arguments?.getString("documento") ?: ""
                            TransferView(currentUser = documento)
                        }
                        composable("profile/{documentNumber}") { backStackEntry ->
                            val documentNumber = backStackEntry.arguments?.getString("documentNumber") ?: ""
                            ProfileView(documentNumber = documentNumber, navController = navController)
                        }
                    }
                }
            }
        }
    }
}