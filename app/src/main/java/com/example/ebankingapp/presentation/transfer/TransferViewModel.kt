package com.example.ebankingapp.presentation.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import com.example.ebankingapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val repository: AccountRepository): ViewModel(){
    private val _state = MutableStateFlow(TransferState())
    val state = _state.asStateFlow()

    fun onRecipientNameChange(newValue: String){
        _state.value = _state.value.copy(recipientName = newValue);
    }

    fun onRecipientIbanChange(newValue: String){
        _state.value = _state.value.copy(recipientIban = newValue);
    }

    fun onAmountChange(newValue: String){
        _state.value = _state.value.copy(amount = newValue);
    }

    fun onSendMoney(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val amountDouble = _state.value.amount.toDoubleOrNull()
            if (amountDouble == null || amountDouble <= 0) {
                _state.value = _state.value.copy(isLoading = false, error = "Please enter a valid amount")
                return@launch
            }

            val currentAccount = repository.getMyAccount().first()

            if (currentAccount == null) {
                _state.value = _state.value.copy(isLoading = false, error = "Current account not found")
                return@launch
            }

            if (currentAccount.balance < amountDouble) {
                _state.value = _state.value.copy(isLoading = false, error = "Insufficient funds")
                return@launch
            }

            try {
                val newBalance = currentAccount.balance - amountDouble
                repository.updateBalance(currentAccount.id, newBalance)

                repository.insertTransaction(
                    TransactionEntity(
                        amount = amountDouble,
                        counterPartyName = _state.value.recipientName,
                        date = System.currentTimeMillis(),
                        type = "OUT"

                    )
                )
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
