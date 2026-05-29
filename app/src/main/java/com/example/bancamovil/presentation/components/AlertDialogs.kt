package com.example.bancamovil.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bancamovil.R

@Composable
fun ShowLoadingAlertDialog() {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.text_loading), color = MaterialTheme.colorScheme.onSurface) },
        text = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        },
        confirmButton = { },
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun ShowMessageAlertDialog(
    onConfirmation: () -> Unit,
    dialogTitle: Int,
    dialogText: Int
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(dialogTitle), color = MaterialTheme.colorScheme.onSurface) },
        text = { Text(stringResource(dialogText), color = MaterialTheme.colorScheme.secondary) },
        confirmButton = {
            Button(
                onClick = { onConfirmation() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.btn_accept), color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}