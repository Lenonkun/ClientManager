package com.example.clientmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientmanager.ui.clients.AddSessionScreen
import com.example.clientmanager.ui.clients.ClientDetailsScreen
import com.example.clientmanager.ui.clients.ClientsScreen
import com.example.clientmanager.ui.clients.EditClientScreen
import com.example.clientmanager.ui.clients.ClientsViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManagerClientsApp()
        }
    }
}

@Composable
fun ManagerClientsApp() {
    val navController: NavHostController = rememberNavController()
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "clients"
        ) {
            composable("clients") {
                val clientsViewModel: ClientsViewModel = viewModel()
                ClientsScreen(navController = navController, clientsViewModel = clientsViewModel)
            }

//            composable("calendar") { CalendarScreen(navController) }
//            composable("home") { HomeScreen(navController) }
//            composable("resources_control") { ResourcesControlScreen(navController) }
//            composable("resources") { ResourcesScreen(navController) }

            composable("add_session/{clientId}") { backStackEntry ->
                val clientId = backStackEntry.arguments?.getString("clientId")?.toIntOrNull() ?: return@composable
                val clientsViewModel: ClientsViewModel = viewModel()
                AddSessionScreen(
                    navController = navController,
                    clientId = clientId,
                    onSave = { session ->
                        val updatedSession = session.copy(clientId = clientId) // Убедитесь, что метод copy доступен
                        clientsViewModel.addSession(updatedSession)
                    }
                )
            }

            composable("edit_client/{clientId}/{clientName}") { backStackEntry ->
                val clientId = backStackEntry.arguments?.getString("clientId")?.toIntOrNull() ?: 0
                val clientName = backStackEntry.arguments?.getString("clientName") ?: ""
                val clientsViewModel: ClientsViewModel = viewModel()
                EditClientScreen(
                    navController = navController,
                    clientId = clientId,
                    initialName = clientName,
                    onSave = { updatedName ->
                        clientsViewModel.updateClientName(clientId, updatedName)
                    }
                )
            }

            composable("client_details/{clientId}") { backStackEntry ->
                val clientId = backStackEntry.arguments?.getString("clientId")?.toIntOrNull() ?: return@composable
                val clientsViewModel: ClientsViewModel = viewModel()
                ClientDetailsScreen(
                    navController = navController,
                    clientId = clientId,
                    clientsViewModel = clientsViewModel
                )
            }
        }
    }
}
