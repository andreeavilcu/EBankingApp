package com.example.ebankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ebankingapp.presentation.dashboard.DashboardScreen
import com.example.ebankingapp.ui.theme.EBankingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EBankingAppTheme {
                DashboardScreen()
            }
        }
    }
}

