package com.example.clientmanager.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clientmanager.viewmodel.SessionViewModel

data class Session(
    val id: Int,
    val clientId: Int,
    val date: String,
    val procedureType: String,
    val zone: String,
    val durationMinutes: Int,
    val resourcesUsed: List<String>,
    val photos: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailsScreen(
    session: Session,
    viewModel: SessionViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        val uriStrings = uris.map { it.toString() } // Преобразуем Uri в String
        viewModel.addPhotosToSession(session.id, uriStrings)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Сеанс от ${session.date}") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Вид процедуры: ${session.procedureType}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Зона: ${session.zone}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Галерея сеанса:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(session.photos) { photoUrl ->
                    PhotoThumbnail(photoUrl = photoUrl)
                }
            }

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Загрузить фотографии")
            }
        }
    }
}
