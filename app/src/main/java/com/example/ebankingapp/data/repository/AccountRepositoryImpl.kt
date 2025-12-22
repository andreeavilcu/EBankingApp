package com.example.ebankingapp.data.repository

import com.example.ebankingapp.data.local.account.AccountDao
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import com.example.ebankingapp.domain.repository.AccountRepository
import com.example.ebankingapp.data.local.transaction.TransactionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val  transactionDao: TransactionDao
): AccountRepository {
    override fun getMyAccount(): Flow<AccountEntity?>{
        return accountDao.getMyAccount()
    }

    override suspend fun insertAccount(account: AccountEntity){
        accountDao.insertAccount(account)
    }

    override suspend fun updateBalance(id: Int, newBalance: Double){
        accountDao.updateBalance(id, newBalance)
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> {
       return transactionDao.getAllTransactions();
    }

    override suspend fun insertTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)

    }

    override suspend fun resetLocalData() {
       accountDao.deleteAll()
        transactionDao.deleteAll()
    }


}