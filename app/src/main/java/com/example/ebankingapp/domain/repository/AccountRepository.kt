package com.example.ebankingapp.domain.repository

import com.example.ebankingapp.data.local.account.AccountEntity
import kotlinx.coroutines.flow.Flow
import com.example.ebankingapp.data.local.transaction.*

interface AccountRepository {
    fun getMyAccount(): Flow<AccountEntity?>
    suspend fun insertAccount(account: AccountEntity)
    suspend fun updateBalance(id: Int, newBalance: Double)

    fun getAllTransactions(): Flow<List<TransactionEntity>>
    suspend fun insertTransaction(transaction: TransactionEntity)
}