package com.example.restapp.data.model

/**
 * Modèle représentant un Post de JSONPlaceholder.
 * id est nullable car lors de la création, l'API attribue l'id.
 */
data class Post(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val body: String
)