package com.example.restapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.restapp.ui.screens.PostDetailScreen
import com.example.restapp.ui.screens.PostFormScreen
import com.example.restapp.ui.screens.PostListScreen
import com.example.restapp.viewmodel.PostViewModel

/**
 * Graphe de navigation de l'application.
 * ⭐ CORRIGÉ : le ViewModel est créé ICI et partagé entre tous les écrans.
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // ⭐ CORRIGÉ : ViewModel partagé (scoped à l'Activity)
    val vm: PostViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "posts"
    ) {
        // ── LISTE DES POSTS ─────────────────────────────
        composable("posts") {
            PostListScreen(
                vm = vm,
                onOpen = { id ->
                    navController.navigate("details/$id")
                },
                onCreate = {
                    navController.navigate("form")
                }
            )
        }

        // ── DÉTAIL D'UN POST ────────────────────────────
        composable("details/{id}") { backStack ->
            val id = backStack.arguments
                ?.getString("id")
                ?.toIntOrNull() ?: 0

            PostDetailScreen(
                id = id,
                vm = vm,
                onBack = {
                    navController.popBackStack()
                },
                onEdit = {
                    navController.navigate("form/$id")
                }
            )
        }

        // ── CRÉATION D'UN POST ──────────────────────────
        composable("form") {
            PostFormScreen(
                postId = null,
                vm = vm,
                onDone = { navController.popBackStack() }
            )
        }

        // ── MODIFICATION D'UN POST ──────────────────────
        composable("form/{id}") { backStack ->
            val id = backStack.arguments
                ?.getString("id")
                ?.toIntOrNull()

            PostFormScreen(
                postId = id,
                vm = vm,
                onDone = { navController.popBackStack() }
            )
        }
    }
}