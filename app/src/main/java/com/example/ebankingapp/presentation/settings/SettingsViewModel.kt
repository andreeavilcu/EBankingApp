package com.example.ebankingapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebankingapp.data.local.account.AccountEntity
import com.example.ebankingapp.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.launch
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: AccountRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        repository.getMyAccount().onEach { account ->
            _state.value = _state.value.copy(account = account)
        }.launchIn(viewModelScope)

    }

    fun onResetApp(onSuccess: () -> Unit){
        viewModelScope.launch {
            repository.resetLocalData()

            val prefs = context.getSharedPreferences("ebanking_prefs", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            onSuccess()
        }
    }
}