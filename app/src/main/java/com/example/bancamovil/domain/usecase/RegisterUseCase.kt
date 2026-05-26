package com.example.bancamovil.domain.usecase

import com.example.bancamovil.domain.model.User
import com.example.bancamovil.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    operator fun invoke(user: User, onResult: (Boolean, Int) -> Unit) {
        repository.register(user, onResult)
    }
}