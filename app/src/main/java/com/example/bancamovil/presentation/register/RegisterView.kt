package com.example.bancamovil.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bancamovil.R
import com.example.bancamovil.presentation.login.Pink

@Composable
fun RegisterView(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController
) {
    var fullName by remember { mutableStateOf("") }
    var documentNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showLoadingAlert by remember { mutableStateOf(false) }
    var showMessageAlert by remember { mutableStateOf(false) }
    var titleDialog by remember { mutableStateOf("") }
    var messageDialog by remember { mutableIntStateOf(0) }

    if (showLoadingAlert) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text(stringResource(R.string.text_loading), color = Color.White) },
            containerColor = Color(0xFF1A1A1A)
        )
    }

    if (showMessageAlert) {
        AlertDialog(
            onDismissRequest = { showMessageAlert = false },
            confirmButton = {
                TextButton(onClick = { showMessageAlert = false }) {
                    Text(stringResource(R.string.btn_accept), color = Pink)
                }
            },
            title = { Text(titleDialog, color = Color.White) },
            text = { Text(stringResource(id = messageDialog), color = Color.Gray) },
            containerColor = Color(0xFF1A1A1A)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title zone
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).wrapContentHeight(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.login_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.register_title),
                fontSize = 18.sp,
                color = Pink,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Form + Buttons
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Pink,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Pink
            )

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(stringResource(R.string.label_full_name), color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            OutlinedTextField(
                value = documentNumber,
                onValueChange = { documentNumber = it },
                label = { Text(stringResource(R.string.label_document_number), color = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.label_password), color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(stringResource(R.string.label_confirm_password), color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    showLoadingAlert = true
                    viewModel.register(fullName, documentNumber, password, confirmPassword) { success, message ->
                        titleDialog = if (success) "Éxito" else "Error"
                        messageDialog = message
                        showLoadingAlert = false
                        showMessageAlert = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink)
            ) {
                Text(stringResource(R.string.btn_register), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.text_already_have_account), color = Color.Gray, fontSize = 13.sp)
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(stringResource(R.string.text_login_here), color = Pink, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}