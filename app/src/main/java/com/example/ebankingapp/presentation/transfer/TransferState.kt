package com.example.ebankingapp.presentation.transfer

data class TransferState(
    val recipientName: String = "",
    val recipientIban: String = "",
    val amount: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
