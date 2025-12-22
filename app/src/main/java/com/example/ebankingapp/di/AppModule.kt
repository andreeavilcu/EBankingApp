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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.ebankingapp.data.remote.CurrencyApi

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
    fun provideAccountRepository(
        dao: AccountDao,
        transactionDao: com.example.ebankingapp.data.local.transaction.TransactionDao,
        api: CurrencyApi
    ): com.example.ebankingapp.domain.repository.AccountRepository {
        return com.example.ebankingapp.data.repository.AccountRepositoryImpl(dao, transactionDao, api)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): com.example.ebankingapp.data.local.transaction.TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://open.er-api.com/v6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }


}
