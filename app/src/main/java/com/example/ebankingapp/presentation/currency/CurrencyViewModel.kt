package com.example.ebankingapp.presentation.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebankingapp.domain.model.CurrencyModel
import com.example.ebankingapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel(){
    private val _state = MutableStateFlow(CurrencyState())
    val state = _state.asStateFlow()

    private var rawRates: Map<String, Double> = emptyMap()

    init {
        loadRates()
    }

    private fun loadRates(){
        viewModelScope.launch{
            _state.value = _state.value.copy(isLoading = true)
            try {
                rawRates = repository.getCurrencyRates()
                calculateConversion(_state.value.amountInRon)
            } catch (e: Exception){
                _state.value = _state.value.copy(error = "Eroare la conexiune", isLoading = false)
            }

        }
    }


    fun onAmountChange(newAmount: String){
        if(newAmount.all {it.isDigit() || it == '.'}){
            calculateConversion(newAmount)
        }
    }

    private fun calculateConversion(amountStr: String){
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        val convertedList = rawRates.map { (code, rate) ->
            CurrencyModel(
                code = code,
                value = rate,
                convertedValue = amount * rate
            )
        }.sortedBy { it.code }

        _state.value = _state.value.copy(
            isLoading = false,
            amountInRon = amountStr,
            currencies = convertedList
        )


    }
}