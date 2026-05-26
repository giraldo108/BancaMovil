package com.example.bancamovil.presentation.register

import androidx.lifecycle.ViewModel
import com.example.bancamovil.R
import com.example.bancamovil.data.repository.FirebaseAuthRepositoryImpl
import com.example.bancamovil.domain.model.User
import com.example.bancamovil.domain.usecase.RegisterUseCase

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase = RegisterUseCase(FirebaseAuthRepositoryImpl())
) : ViewModel() {

    fun register(
        fullName: String,
        documentNumber: String,
        password: String,
        confirmPassword: String,
        onResult: (Boolean, String) -> Unit
    ) {
        if (fullName.isBlank() || documentNumber.isBlank() ||
            password.isBlank() || confirmPassword.isBlank()
        ) {
            onResult(false, "Por favor completa todos los campos")
            return
        }
        if (documentNumber.length < 6) {
            onResult(false, "El documento debe tener al menos 6 dígitos")
            return
        }
        if (password != confirmPassword) {
            onResult(false, "Las contraseñas no coinciden")
            return
        }
        val user = User(
            fullName = fullName,
            documentNumber = documentNumber,
            password = password
        )
        registerUseCase(user) { success, _ ->
            if (success) {
                onResult(true, "Cuenta creada correctamente")
            } else {
                onResult(false, "Hubo un error al crear la cuenta")
            }
        }
    }
}