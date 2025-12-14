package com.example.ebankingapp.presentation.register

data class RegisterState(
    val name: String = "",
    val pin: String = "",
    val confirmPin: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)