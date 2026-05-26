package com.example.bancamovil.data.repository

import android.util.Log
import com.example.bancamovil.data.datasource.FirebaseUserDataSource
import com.example.bancamovil.domain.model.User
import com.example.bancamovil.domain.repository.AuthRepository

class FirebaseAuthRepositoryImpl(
    private val dataSource: FirebaseUserDataSource = FirebaseUserDataSource()
) : AuthRepository {

    override fun login(
        documentNumber: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {

        dataSource.getUser(documentNumber)
            .addOnSuccessListener { dataUser ->

                println("FIREBASE DATA: ${dataUser.value}")

                if (!dataUser.exists()) {
                    onResult(false, "Usuario no encontrado")
                    return@addOnSuccessListener
                }

                val dbPassword =
                    dataUser.child("contrasena").value.toString()

                println("PASSWORD FIREBASE: $dbPassword")
                println("PASSWORD APP: $password")

                if (dbPassword.trim() == password.trim()) {

                    onResult(true, "")

                } else {

                    onResult(false, "Contraseña incorrecta")
                }
            }

            .addOnFailureListener { e ->

                println("ERROR FIREBASE: ${e.message}")

                onResult(false, "Error de conexión")
            }
    }
    override fun register(user: User, onResult: (Boolean, String) -> Unit) {

        val userData = mapOf(
            "fullName" to user.fullName,
            "contrasena" to user.password
        )

        dataSource.saveUser(user.documentNumber, userData)

            .addOnSuccessListener {

                onResult(true, "Cuenta creada correctamente")
            }

            .addOnFailureListener {

                onResult(false, "Error al registrar usuario")
            }
    }
}