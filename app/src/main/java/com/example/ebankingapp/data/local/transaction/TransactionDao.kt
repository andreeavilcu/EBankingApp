package com.example.ebankingapp.data.local.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun inertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAllTransactions(): Flow<List<TransactionEntity>>

}