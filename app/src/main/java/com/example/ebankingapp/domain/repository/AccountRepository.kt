package com.example.ebankingapp.domain.repository

import com.example.ebankingapp.data.local.account.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getMyAccount(): Flow<AccountEntity?>
    suspend fun insertAccount(account: AccountEntity)
    suspend fun updateBalance(id: Int, newBalance: Double)
}