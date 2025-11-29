package com.example.ebankingapp.di

import android.content.Context
import androidx.room.Room
import com.example.ebankingapp.data.local.account.AccountDao
import com.example.ebankingapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ebanking_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Provides
    @Singleton
    fun provideAccountRepository(dao: AccountDao): com.example.ebankingapp.domain.repository.AccountRepository {
        return com.example.ebankingapp.data.repository.AccountRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): com.example.ebankingapp.data.local.transaction.TransactionDao {
        return database.transactionDao()
    }


}