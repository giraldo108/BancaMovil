package com.example.bancamovil.data.repository

import com.example.bancamovil.R
import com.example.bancamovil.data.datasource.FirebaseUserDataSource
import com.example.bancamovil.domain.model.User
import com.example.bancamovil.domain.repository.AuthRepository

class FirebaseAuthRepositoryImpl(
    private val dataSource: FirebaseUserDataSource = FirebaseUserDataSource()
) : AuthRepository {

    override fun login(
        documentNumber: String,
        password: String,
        onResult: (Boolean, Int) -> Unit
    ) {
        dataSource.getUser(documentNumber)
            .addOnSuccessListener { dataUser ->
                if (!dataUser.exists()) {
                    onResult(false, R.string.error_login_failed)
                    return@addOnSuccessListener
                }
                val dbPassword = dataUser.child("contrasena").value.toString()
                if (dbPassword.trim() == password.trim()) {
                    onResult(true, 0)
                } else {
                    onResult(false, R.string.error_login_failed)
                }
            }
            .addOnFailureListener {
                onResult(false, R.string.error_login_failed)
            }
    }

    override fun register(user: User, onResult: (Boolean, Int) -> Unit) {
        val userData = mapOf(
            "fullName" to user.fullName,
            "contrasena" to user.password
        )
        dataSource.saveUser(user.documentNumber, userData)
            .addOnSuccessListener { onResult(true, R.string.register_success_message) }
            .addOnFailureListener { onResult(false, R.string.error_register_failed) }
    }
}