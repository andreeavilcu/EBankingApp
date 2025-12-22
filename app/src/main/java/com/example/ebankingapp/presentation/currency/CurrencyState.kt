package com.example.ebankingapp.presentation.currency
import com.example.ebankingapp.domain.model.CurrencyModel

data class CurrencyState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val amountInRon: String = "100",
    val currencies: List<CurrencyModel> = emptyList()
)
