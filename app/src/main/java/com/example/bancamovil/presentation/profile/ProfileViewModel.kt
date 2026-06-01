package com.example.bancamovil.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.bancamovil.data.datasource.FirebaseUserDataSource

class ProfileViewModel(
    private val dataSource: FirebaseUserDataSource = FirebaseUserDataSource()
) : ViewModel() {

    fun loadProfile(
        documentNumber: String,
        onResult: (Boolean, Map<String, String>) -> Unit
    ) {
        dataSource.getUser(documentNumber)
            .addOnSuccessListener { snapshot ->
                if (!snapshot.exists()) {
                    onResult(false, emptyMap())
                    return@addOnSuccessListener
                }
                val data = mapOf(
                    "fullName" to (snapshot.child("fullName").value?.toString() ?: ""),
                    "documentNumber" to documentNumber,
                    "contrasena" to (snapshot.child("contrasena").value?.toString() ?: ""),
                    "saldo" to (snapshot.child("saldo").value?.toString() ?: "0")
                )
                onResult(true, data)
            }
            .addOnFailureListener {
                onResult(false, emptyMap())
            }
    }
}