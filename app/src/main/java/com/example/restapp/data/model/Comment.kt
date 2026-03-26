package com.example.restapp.data.model

/**
 * Modèle représentant un Commentaire d'un post.
 */
data class Comment(
    val postId: Int,
    val id: Int? = null,
    val name: String,
    val email: String,
    val body: String
)