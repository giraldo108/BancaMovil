package com.example.bancamovil.presentation.transfer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bancamovil.R
import com.google.firebase.database.FirebaseDatabase

class TransferViewModel : ViewModel() {

    val accountNumber = mutableStateOf("")
    val amount = mutableStateOf("")
    val message = mutableStateOf(0)

    private val database = FirebaseDatabase.getInstance().getReference("usuarios")

    fun onAccountChange(value: String) { accountNumber.value = value }
    fun onAmountChange(value: String) { amount.value = value }

    fun transfer(currentUser: String) {
        if (accountNumber.value.isEmpty() || amount.value.isEmpty()) {
            message.value = R.string.error_fields_empty
            return
        }

        val transferAmount = amount.value.toDoubleOrNull()
        if (transferAmount == null || transferAmount <= 0) {
            message.value = R.string.msg_invalid_amount
            return
        }

        database.child(currentUser).get()
            .addOnSuccessListener { senderSnapshot ->
                val senderBalance = senderSnapshot.child("saldo").getValue(Double::class.java) ?: 0.0

                if (senderBalance < transferAmount) {
                    message.value = R.string.msg_insufficient_balance
                    return@addOnSuccessListener
                }

                database.child(accountNumber.value).get()
                    .addOnSuccessListener { receiverSnapshot ->
                        if (!receiverSnapshot.exists()) {
                            message.value = R.string.msg_account_not_found
                            return@addOnSuccessListener
                        }

                        val receiverBalance = receiverSnapshot.child("saldo").getValue(Double::class.java) ?: 0.0

                        database.child(currentUser).child("saldo").setValue(senderBalance - transferAmount)
                        database.child(accountNumber.value).child("saldo").setValue(receiverBalance + transferAmount)

                        message.value = R.string.msg_transfer_success
                    }
            }
    }
}