package com.example.bancamovil.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class HomeViewModel : ViewModel() {

    val fullName = mutableStateOf("")
    val saldo = mutableStateOf(0.0)

    private val database = FirebaseDatabase.getInstance()
        .getReference("usuarios")

    fun cargarDatos(documento: String) {
        database.child(documento).get()
            .addOnSuccessListener { snapshot ->
                fullName.value =
                    snapshot.child("fullName").getValue(String::class.java)
                        ?: snapshot.child("nombre").getValue(String::class.java)
                                ?: ""
                saldo.value = snapshot.child("saldo").getValue(Double::class.java) ?: 0.0
            }
    }
}