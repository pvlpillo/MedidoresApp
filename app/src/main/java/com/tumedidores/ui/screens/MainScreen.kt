package com.tumedidores.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.tumedidores.data.database.AppDatabase
import com.tumedidores.data.repository.MedicionRepository
import com.tumedidores.navigation.AppNavigation
import com.tumedidores.ui.viewmodels.MedicionViewModel

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val viewModel = remember {
        val database = AppDatabase.getDatabase(context)
        val repository = MedicionRepository(database.medicionDao())
        MedicionViewModel(repository)
    }

    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavigation(navController = navController, viewModel = viewModel)
    }
}