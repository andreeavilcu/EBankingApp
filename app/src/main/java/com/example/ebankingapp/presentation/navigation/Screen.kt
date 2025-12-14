package com.example.ebankingapp.presentation.navigation

sealed class Screen(val route: String){
    object Register : Screen("register_screen")
    object Login : Screen("login_screen")
    object Dashboard: Screen("dashboard_screen")
    object Transfer: Screen("transfer_screen")
}