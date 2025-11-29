package com.example.ebankingapp.data.local.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val iban: String,
    val holderName: String,
    val balance: Double,
    val currency: String
)