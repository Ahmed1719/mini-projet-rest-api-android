package com.example.restapp.viewmodel

/**
 * Sealed class représentant les 3 états possibles de l'UI :
 * - Loading : chargement en cours
 * - Success : données reçues avec succès
 * - Error   : une erreur est survenue
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}