package com.example.ebankingapp.presentation.settings

import com.example.ebankingapp.data.local.account.AccountEntity
data class SettingsState(
    val account: AccountEntity? = null
)