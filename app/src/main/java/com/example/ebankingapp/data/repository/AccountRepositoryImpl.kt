package com.example.ebankingapp.data.repository

import com.example.ebankingapp.data.local.account.AccountDao
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
): AccountRepository {
    override fun getMyAccount(): Flow<AccountEntity?>{
        return dao.getMyAccount()
    }

    override suspend fun insertAccount(account: AccountEntity){
        dao.insertAccount(account)
    }

    override suspend fun updateBalance(id: Int, newBalance: Double){
        dao.updateBalance(id, newBalance)
    }
}