package com.example.ebankingapp.data.local.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ebankingapp.data.local.account.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts LIMIT 1")
    fun getMyAccount(): Flow<AccountEntity?>

    @Query("UPDATE accounts SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateBalance(accountId: Int, newBalance: Double)
}