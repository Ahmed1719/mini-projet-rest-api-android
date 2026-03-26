package com.example.restapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.restapp.viewmodel.PostViewModel
import com.example.restapp.viewmodel.UiState

/**
 * Écran Liste — affiche tous les posts dans une LazyColumn.
 * Gère les états : Chargement, Erreur, Vide, Succès.
 */
@Composable
fun PostListScreen(
    vm: PostViewModel,
    onOpen: (Int) -> Unit,
    onCreate: () -> Unit
) {
    val state by vm.posts.collectAsState()

    // Charger les posts au premier affichage
    LaunchedEffect(Unit) {
        vm.loadPosts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreate) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Erreur : ${(state as UiState.Error).message}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { vm.loadPosts() }) {
                            Text("Réessayer")
                        }
                    }
                }

                is UiState.Success -> {
                    val posts = (state as UiState.Success).data
                    if (posts.isEmpty()) {
                        Text("Aucun post disponible")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(posts) { post ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .clickable {
                                            post.id?.let { onOpen(it) }
                                        },
                                    elevation = CardDefaults.cardElevation(6.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = post.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        // ⭐ CORRIGÉ : body tronqué (résumé)
                                        Text(
                                            text = post.body,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}