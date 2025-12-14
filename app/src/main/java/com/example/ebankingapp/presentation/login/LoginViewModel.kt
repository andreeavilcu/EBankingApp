package com.example.ebankingapp.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.MessageDigest
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences("ebanking_prefs", Context.MODE_PRIVATE)

    init {
        checkIfPinSet()
    }

    private fun checkIfPinSet(){
        val savedHash = sharedPreferences.getString("pin_hash", null)
        if (savedHash != null) {
            _state.value = _state.value.copy(isPinCreated = true, message = "Enter PIN for access")
        } else {
            _state.value = _state.value.copy(isPinCreated = false, message = "Create a new 4-digit PIN")
        }

    }

    fun onPinEntered(pin: String, onSuccess: () -> Unit) {
        val currentPin = _state.value.inputPin + pin

        if (currentPin.length <= 4) {
            _state.value = _state.value.copy(inputPin = currentPin, isError = false)
        }

        if (currentPin.length == 4) {
            val inputHash = hashString(currentPin)
            val savedHash = sharedPreferences.getString("pin_hash", "")

            if (inputHash == savedHash) {
                onSuccess()
            } else {
                _state.value = _state.value.copy(inputPin = "", isError = true, message = "PIN Incorect.")
            }
        }
    }

    fun clearInput(){
        _state.value = _state.value.copy(inputPin = "", isError = false)
    }

    private fun hashString(input: String): String{
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }



}
