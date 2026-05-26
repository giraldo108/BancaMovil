package com.example.bancamovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bancamovil.presentation.home.HomeView
import com.example.bancamovil.presentation.login.LoginView
import com.example.bancamovil.presentation.register.RegisterView
import com.example.bancamovil.presentation.transfer.TransferView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {

                    // LOGIN
                    composable("login") {

                        LoginView(
                            navController = navController
                        )
                    }

                    // REGISTER
                    composable("register") {

                        RegisterView(
                            navController = navController
                        )
                    }

                    // HOME
                    composable("home/{documento}") { backStackEntry ->

                        val documento =
                            backStackEntry.arguments
                                ?.getString("documento") ?: ""

                        HomeView(
                            navController = navController,
                            documento = documento
                        )
                    }

                    // TRANSFERENCIAS
                    composable("transactions/{documento}") { backStackEntry ->

                        val documento =
                            backStackEntry.arguments
                                ?.getString("documento") ?: ""

                        TransferView(
                            currentUser = documento
                        )
                    }
                }
            }
        }
    }
}