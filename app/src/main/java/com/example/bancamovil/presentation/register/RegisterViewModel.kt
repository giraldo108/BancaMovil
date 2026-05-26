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
        onResult: (Boolean, Int) -> Unit
    ) {
        if (fullName.isBlank() || documentNumber.isBlank() ||
            password.isBlank() || confirmPassword.isBlank()
        ) {
            onResult(false, R.string.error_fields_empty )
            return
        }
        if (documentNumber.length < 6) {
            onResult(false, R.string.error_document_length)
            return
        }
        if (password != confirmPassword) {
            onResult(false, R.string.error_passwords_match)
            return
        }
        val user = User(
            fullName = fullName,
            documentNumber = documentNumber,
            password = password
        )
        registerUseCase(user) { success, _ ->
            if (success) {
                onResult(true, R.string.register_success_message)
            } else {
                onResult(false, R.string.error_register_failed)
            }
        }
    }
}