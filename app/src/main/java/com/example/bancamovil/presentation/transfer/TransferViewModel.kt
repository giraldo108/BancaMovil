package com.example.bancamovil.presentation.transfer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class TransferViewModel : ViewModel() {

    val accountNumber = mutableStateOf("")
    val amount = mutableStateOf("")
    val message = mutableStateOf("")

    private val database =
        FirebaseDatabase.getInstance().getReference("usuarios")

    fun onAccountChange(value: String) {
        accountNumber.value = value
    }

    fun onAmountChange(value: String) {
        amount.value = value
    }

    fun transfer(currentUser: String) {

        if (
            accountNumber.value.isEmpty() ||
            amount.value.isEmpty()
        ) {

            message.value = "Completa todos los campos"
            return
        }

        val transferAmount = amount.value.toDoubleOrNull()

        if (transferAmount == null || transferAmount <= 0) {

            message.value = "Monto inválido"
            return
        }

        database.child(currentUser).get()

            .addOnSuccessListener { senderSnapshot ->

                val senderBalance =
                    senderSnapshot.child("saldo")
                        .getValue(Double::class.java) ?: 0.0

                if (senderBalance < transferAmount) {

                    message.value = "Saldo insuficiente"
                    return@addOnSuccessListener
                }

                database.child(accountNumber.value).get()

                    .addOnSuccessListener { receiverSnapshot ->

                        if (!receiverSnapshot.exists()) {

                            message.value = "Cuenta no existe"
                            return@addOnSuccessListener
                        }

                        val receiverBalance =
                            receiverSnapshot.child("saldo")
                                .getValue(Double::class.java) ?: 0.0

                        val newSenderBalance =
                            senderBalance - transferAmount

                        val newReceiverBalance =
                            receiverBalance + transferAmount

                        database.child(currentUser)
                            .child("saldo")
                            .setValue(newSenderBalance)

                        database.child(accountNumber.value)
                            .child("saldo")
                            .setValue(newReceiverBalance)

                        message.value =
                            "Transferencia realizada"
                    }
            }
    }
}