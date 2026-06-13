package com.example.labo04luisguzman00003924.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labo04luisguzman00003924.TaskApplication
import com.example.labo04luisguzman00003924.ViewModel.GeneralViewModel
import com.example.labo04luisguzman00003924.ui.theme.HomeScreen
import com.example.labo04luisguzman00003924.ui.theme.TODOScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as TaskApplication
    val viewModel: GeneralViewModel = viewModel(
        factory = GeneralViewModel.Factory(application.repository)
    )

    NavHost(
        navController = navController,
        startDestination = "home_screen",
        modifier = modifier
    ) {
        composable("home_screen") {
            HomeScreen(
                onNavigateToList = {
                    navController.navigate("todo_screen")
                }
            )
        }

        composable("todo_screen") {
            TODOScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
