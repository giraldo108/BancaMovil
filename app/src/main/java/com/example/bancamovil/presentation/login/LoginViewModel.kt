package com.example.bancamovil.presentation.login

import androidx.lifecycle.ViewModel
import com.example.bancamovil.data.repository.FirebaseAuthRepositoryImpl
import com.example.bancamovil.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase = LoginUseCase(FirebaseAuthRepositoryImpl())
) : ViewModel() {

    fun login(
        documentNumber: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        if (documentNumber.isBlank() || password.isBlank()) {
            onResult(false, "Por favor completa todos los campos")
            return
        }
        if (documentNumber.length < 6) {
            onResult(false, "El documento debe tener al menos 6 dígitos")
            return
        }
        loginUseCase(documentNumber, password) { success, _ ->
            if (success) {
                onResult(true, "")
            } else {
                onResult(false, "Credenciales incorrectas")
            }
        }
    }
}