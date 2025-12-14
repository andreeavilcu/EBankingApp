package com.example.ebankingapp.presentation.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import com.example.ebankingapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AccountRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences("ebanking_prefs", Context.MODE_PRIVATE)

    fun onNameChange(value: String){
        _state.value = _state.value.copy(name = value)
    }

    fun onPinChange(value: String){
        _state.value = _state.value.copy(pin = value)
    }

    fun onConfirmPinChange(value: String){
        _state.value = _state.value.copy(confirmPin = value)
    }

    fun onRegister(){
        val currentState = _state.value

        if (currentState.name.isBlank()) {
            _state.value = currentState.copy(error = "Enter your full name")
            return
        }
        if (currentState.pin.length != 4) {
            _state.value = currentState.copy(error = "the PIN must have 4 digits")
            return
        }
        if (currentState.pin != currentState.confirmPin) {
            _state.value = currentState.copy(error = "PINs do not match")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            val pinHash = hashString(currentState.pin)
            sharedPreferences.edit().putString("pin_hash", pinHash).apply()

            val newAccount = AccountEntity(
                iban = "RO98 BTRL " + (1000..9999).random() + " " + (1000..9999).random() + " XX",
                holderName = currentState.name,
                balance = 1000.00,
                currency = "RON"
            )
            repository.insertAccount(newAccount)

            repository.insertTransaction(
                TransactionEntity(
                    amount = 1000.0,
                    counterPartyName = "Account Opening Bonus",
                    date = System.currentTimeMillis(),
                    type = "IN"
                )
            )

            _state.value = currentState.copy(isLoading = false, isSuccess = true)
        }

    }

    private fun hashString(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }


}