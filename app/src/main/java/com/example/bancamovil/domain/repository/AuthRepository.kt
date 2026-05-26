package com.example.bancamovil.domain.repository

import com.example.bancamovil.domain.model.User

interface AuthRepository {
    fun login(documentNumber: String, password: String, onResult: (Boolean, String) -> Unit)
    fun register(user: User, onResult: (Boolean, String) -> Unit)
}