package com.example.ebankingapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ebankingapp.presentation.dashboard.DashboardScreen
import com.example.ebankingapp.presentation.navigation.Screen
import com.example.ebankingapp.presentation.transfer.TransferScreen
import com.example.ebankingapp.presentation.login.LoginScreen
import com.example.ebankingapp.ui.theme.EBankingAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.FragmentActivity

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EBankingAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route

                ){
                    composable(route = Screen.Login.route) {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

                    composable(route = Screen.Dashboard.route){
                        DashboardScreen(
                            onNavigateToTransfer = {
                                navController.navigate(Screen.Transfer.route)
                            }
                        )
                    }

                    composable(route = Screen.Transfer.route) {
                        TransferScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

            }
        }
    }
}
}

