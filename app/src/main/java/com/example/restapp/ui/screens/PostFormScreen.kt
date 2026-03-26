package com.example.restapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restapp.data.model.Post
import com.example.restapp.viewmodel.PostViewModel
import com.example.restapp.viewmodel.UiState
import kotlinx.coroutines.launch

/**
 * Écran Formulaire — Créer ou Modifier un post.
 * ⭐ CORRIGÉ :
 *   - Pré-remplissage fonctionnel via observation de postDetail
 *   - Validation des champs vides
 *   - Feedback via SnackBar
 */
@Composable
fun PostFormScreen(
    postId: Int?,
    vm: PostViewModel,
    onDone: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    // ⭐ Flag pour éviter de ré-écraser les champs après modification par l'utilisateur
    var prefilled by remember { mutableStateOf(false) }

    val isEdit = postId != null
    val postDetailState by vm.postDetail.collectAsState()

    // ⭐ CORRIGÉ : charger le post à éditer via l'endpoint dédié
    LaunchedEffect(postId) {
        if (isEdit) {
            vm.loadPostDetail(postId!!)
        }
    }

    // ⭐ CORRIGÉ : observer le changement d'état pour pré-remplir
    LaunchedEffect(postDetailState) {
        if (isEdit && !prefilled) {
            val state = postDetailState
            if (state is UiState.Success) {
                title = state.data.title
                body = state.data.body
                prefilled = true
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            Text(
                if (isEdit) "Modifier Post" else "Créer Post",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(20.dp))

            // Champ Titre
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titre") },
                modifier = Modifier.fillMaxWidth(),
                isError = title.isBlank()
            )

            Spacer(Modifier.height(10.dp))

            // Champ Contenu
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Contenu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                isError = body.isBlank()
            )

            Spacer(Modifier.height(20.dp))

            // Bouton Enregistrer
            Button(
                onClick = {
                    scope.launch {
                        // ⭐ CORRIGÉ : validation des champs non vides
                        if (title.isBlank() || body.isBlank()) {
                            snackbar.showSnackbar("Les champs titre et contenu sont obligatoires !")
                            return@launch
                        }

                        if (isEdit) {
                            vm.updatePost(postId!!, title, body)
                            snackbar.showSnackbar("Post modifié ✔")
                        } else {
                            vm.createPost(title, body)
                            snackbar.showSnackbar("Post créé ✔")
                        }
                        onDone()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enregistrer")
            }

            Spacer(Modifier.height(10.dp))

            // Bouton Annuler
            OutlinedButton(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Annuler")
            }
        }
    }
}