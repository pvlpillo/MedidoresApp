package com.tumedidores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tumedidores.ui.screens.FormularioScreen
import com.tumedidores.ui.screens.ListaMedicionesScreen
import com.tumedidores.ui.viewmodels.MedicionViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MedicionViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "lista_mediciones"
    ) {
        composable("lista_mediciones") {
            ListaMedicionesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("formulario") {
            FormularioScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}