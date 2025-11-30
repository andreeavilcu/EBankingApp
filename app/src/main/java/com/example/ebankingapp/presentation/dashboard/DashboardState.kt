package com.example.ebankingapp.presentation.dashboard

import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.data.local.transaction.*

data class DashboardState (
    val account: AccountEntity? = null,
    val transactions: List<TransactionEntity> = emptyList(),
    val isLoading: Boolean = true
)

