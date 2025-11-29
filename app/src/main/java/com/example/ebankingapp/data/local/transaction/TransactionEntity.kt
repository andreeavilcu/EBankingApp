package com.example.ebankingapp.data.local.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val counterPartyName: String,
    val date: Long,
    val type: String
    )
