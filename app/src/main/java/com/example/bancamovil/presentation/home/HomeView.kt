package com.example.bancamovil.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

val Pink = Color(0xFFFF2D78)

@Composable
fun HomeView(
    navController: NavController,
    documento: String = "",
    viewModel: HomeViewModel = viewModel()
) {
    LaunchedEffect(documento) {
        if (documento.isNotEmpty()) viewModel.cargarDatos(documento)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
    ) {

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF2A2A2A), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👤", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "Hola,", color = Color.Gray, fontSize = 13.sp)
                    Text(
                        text = viewModel.fullName.value.ifEmpty { "Usuario" },
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            TextButton(onClick = { navController.navigate("profile") }) {
                Text(text = "Perfil", color = Pink, fontSize = 14.sp)
            }
        }

        // Tarjeta de saldo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column {
                Text(text = "Disponible", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$ ${String.format("%,.0f", viewModel.saldo.value)}",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Botones de acción
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón Transferir
            Button(
                onClick = { navController.navigate("transactions/$documento") },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink)
            ) {
                Text(
                    text = "Transferir",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            // Botón Historial
            OutlinedButton(
                onClick = { navController.navigate("historial") },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = "Historial",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}