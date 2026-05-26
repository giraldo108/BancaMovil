package com.example.bancamovil.domain.usecase

import com.example.bancamovil.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        documentNumber: String,
        password: String,
        onResult: (Boolean, Int) -> Unit
    ) {
        repository.login(documentNumber, password, onResult)
    }
}