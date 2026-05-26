package com.example.bancamovil.presentation.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

val Pink = Color(0xFFFF2D78)

@Composable
fun TransferView(
    currentUser: String,
    viewModel: TransferViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
            .padding(horizontal = 32.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Transferir Dinero",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Cuenta destino
        OutlinedTextField(
            value = viewModel.accountNumber.value,

            onValueChange = {
                viewModel.onAccountChange(it)
            },

            label = {
                Text(
                    "Cuenta destino",
                    color = Color.Gray
                )
            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier.fillMaxWidth(),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Pink,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Pink
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Monto
        OutlinedTextField(
            value = viewModel.amount.value,

            onValueChange = {
                viewModel.onAmountChange(it)
            },

            label = {
                Text(
                    "Monto",
                    color = Color.Gray
                )
            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier.fillMaxWidth(),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Pink,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Pink
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mensaje
        if (viewModel.message.value.isNotEmpty()) {

            Text(
                text = viewModel.message.value,
                color = if (
                    viewModel.message.value == "Transferencia realizada"
                ) Pink else Color.Red,

                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón
        Button(
            onClick = {

                viewModel.transfer(currentUser)

            },

            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),

            shape = RoundedCornerShape(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Pink
            )
        ) {

            Text(
                text = "Transferir",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}