package com.example.ebankingapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebankingapp.domain.repository.AccountRepository
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AccountRepository) : ViewModel() {

        private val _state = MutableStateFlow(DashboardState())

        val state: StateFlow<DashboardState> = _state.asStateFlow()

        init{

            loadDashBoardData()
        }

    private fun loadDashBoardData() {
        val accountFlow = repository.getMyAccount()
        val transactionsFlow = repository.getAllTransactions()

        combine(accountFlow, transactionsFlow) { account, transactions ->
            DashboardState(
                account = account,
                transactions = transactions,
                isLoading = false
            )
        }.onEach { newState ->
            _state.value = newState
        }.launchIn(viewModelScope)
    }

    }
