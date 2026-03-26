package com.example.restapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restapp.viewmodel.PostViewModel
import com.example.restapp.viewmodel.UiState

/**
 * Écran Détails — affiche un post et ses commentaires.
 * Boutons : Modifier (✏) et Supprimer (🗑).
 *
 * ⭐ CORRIGÉ : utilise loadPostDetail(id) au lieu de charger toute la liste.
 */
@Composable
fun PostDetailScreen(
    id: Int,
    vm: PostViewModel,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    // ⭐ CORRIGÉ : utiliser postDetail au lieu de posts
    val postDetailState by vm.postDetail.collectAsState()
    val commentsState by vm.comments.collectAsState()

    // Charger le détail du post et ses commentaires
    LaunchedEffect(id) {
        vm.loadPostDetail(id)
        vm.loadComments(id)
    }

    Scaffold(
        floatingActionButton = {
            Column {
                // Bouton Modifier
                FloatingActionButton(
                    onClick = onEdit,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text("✏")
                }
                // Bouton Supprimer
                FloatingActionButton(
                    onClick = {
                        vm.deletePost(id)
                        onBack()
                    }
                ) {
                    Text("🗑")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            // ── Section Post ────────────────────────────
            item {
                when (postDetailState) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is UiState.Success -> {
                        // ⭐ CORRIGÉ : accès direct au post (pas de filtre)
                        val post = (postDetailState as UiState.Success).data

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    post.title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(post.body)
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Text(
                            "Commentaires",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    is UiState.Error -> {
                        Text("Erreur : ${(postDetailState as UiState.Error).message}")
                    }
                }
            }

            // ── Section Commentaires ────────────────────
            when (commentsState) {
                is UiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is UiState.Success -> {
                    val comments = (commentsState as UiState.Success).data
                    items(comments) { c ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(
                                    c.name,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    c.email,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(c.body)
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    item { Text("Erreur chargement commentaires") }
                }
            }
        }
    }
}