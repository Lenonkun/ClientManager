package com.example.clientmanager.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.clientmanager.ui.screens.*
import com.example.clientmanager.ui.session.AddSessionScreen
import com.example.clientmanager.viewmodel.ClientViewModel
import com.example.clientmanager.viewmodel.ProcedureTypesViewModel


@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController, startDestination = NavigationItem.Clients.route, Modifier) {
        composable(NavigationItem.Clients.route) {
            ClientsScreen(navController = navController)
        }
        composable(NavigationItem.Sessions.route) { SessionsScreen(navController = navController) }
        composable(NavigationItem.Calendar.route) { CalendarScreen() }
        composable(NavigationItem.Resources.route) {
            val viewModel: ProcedureTypesViewModel = hiltViewModel() // Получаем ViewModel
            val procedureTypes by viewModel.procedureTypes.collectAsState(initial = emptyList()) // Слушаем состояние
            ResourcesScreen(procedureTypes = procedureTypes)
        }

        composable(NavigationItem.More.route) { MoreScreen() }
        composable("addSession") {
            AddSessionScreen(
                sessionViewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }

        composable("clientDetails/{clientId}") { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId")?.toInt() ?: 0
            val clientViewModel: ClientViewModel = hiltViewModel()

            // Загружаем данные клиента при открытии экрана
            LaunchedEffect(clientId) {
                clientViewModel.loadClient(clientId)
            }

            val client by clientViewModel.client.collectAsState()

            if (client != null) {
                ClientDetailsScreen(
                    client = client!!,
                    navController = navController,
                    clientId = clientId,
                    onClose = { navController.popBackStack() }
                )
            } else {
                // Показываем индикатор загрузки или заглушку
                Text("Загрузка данных клиента...")
            }
        }

    }
}
