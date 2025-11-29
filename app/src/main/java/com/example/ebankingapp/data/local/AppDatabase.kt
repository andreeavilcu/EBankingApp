package com.example.ebankingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.data.local.account.AccountDao
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import com.example.ebankingapp.data.local.transaction.TransactionDao

@Database(entities = [AccountEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao
}