package com.example.ebankingapp.presentation.login

data class LoginState(
    val isPinCreated: Boolean = false,
    val inputPin: String = "",
    val isError: Boolean = false,
    val message: String = ""
)