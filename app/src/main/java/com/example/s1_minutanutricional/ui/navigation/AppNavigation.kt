package com.example.s1_minutanutricional.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.s1_minutanutricional.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("recover") {
            RecoverScreen(navController)
        }

        composable("menu") {
            MenuSemanalScreen(navController)
        }

        composable(
            route = "receta/{dia}",
            arguments = listOf(
                navArgument("dia") { type = NavType.StringType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val dia = backStackEntry.arguments?.getString("dia") ?: ""
            RecetaDetalleScreen(dia, navController)
        }
    }
}
