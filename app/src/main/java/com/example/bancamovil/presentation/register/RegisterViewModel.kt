package com.example.bancamovil.presentation.register

// Importa la clase context para acceder a recursos del sistema androit
import android.content.Context

// Importa uri para manejar la ruta de la imagen capturada
import android.net.Uri

// Importa view model para gestionar la logica del negocio
import androidx.lifecycle.ViewModel

// Importa viewModelScope para ejecutar corrutinas
import androidx.lifecycle.viewModelScope

// Importa recursos de strings.xml
import com.example.bancamovil.R

// Importa el DataSource encargado de subir imágenes a Supabase
import com.example.bancamovil.data.datasource.DocumentCameraDataSource

// Importa el repositorio de Firebase
import com.example.bancamovil.data.repository.FirebaseAuthRepositoryImpl

// Importa el modelo User
import com.example.bancamovil.domain.model.User

// Importa el caso de uso de registro
import com.example.bancamovil.domain.usecase.RegisterUseCase

// Importa corrutinas
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase = RegisterUseCase(FirebaseAuthRepositoryImpl()),
    private val documentDataSource: DocumentCameraDataSource = DocumentCameraDataSource()
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
            onResult(false, R.string.error_fields_empty)
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
        registerUseCase(user, onResult)
    }

    fun uploadDocument(
        context: Context,
        imageUri: Uri,
        documentNumber: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val url = documentDataSource.uploadDocument(context, imageUri, documentNumber)
                onResult(true, url)
            } catch (e: Exception) {
                onResult(false, e.message ?: "Error al subir imagen")
            }
        }
    }
}