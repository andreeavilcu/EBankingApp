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
import com.example.ebankingapp.presentation.register.RegisterScreen
import com.example.ebankingapp.presentation.settings.SettingsScreen

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ebanking_prefs", MODE_PRIVATE)
        val hasAccount = prefs.getString("pin_hash", null) != null

        val startScreen = if (hasAccount) Screen.Login.route else Screen.Register.route

        setContent {
            EBankingAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = startScreen

                ){

                    composable(route = Screen.Register.route) {
                        RegisterScreen(
                            onRegistrationSuccess = {
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }

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
                            },
                            onNavigateToSettings = {
                                navController.navigate(Screen.Settings.route)
                            }
                        )
                    }

                    composable(route = Screen.Settings.route) {
                        SettingsScreen(
                            onNavigateBack = { navController.popBackStack() },
                            onLogout = {
                                navController.navigate(Screen.Login.route) { popUpTo(0) }
                            },
                            onResetApp = {
                                navController.navigate(Screen.Register.route) {
                                    popUpTo(0)
                                }
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

