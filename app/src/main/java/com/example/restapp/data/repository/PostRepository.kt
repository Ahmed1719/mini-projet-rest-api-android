package com.example.restapp.data.repository

import com.example.restapp.data.model.Post
import com.example.restapp.data.remote.RetrofitClient

/**
 * Repository — couche d'accès aux données.
 * Encapsule les appels API et expose des méthodes métier.
 */
class PostRepository {

    private val api = RetrofitClient.api

    /** Récupérer la liste de tous les posts */
    suspend fun getPosts() = api.getPosts()

    /** Récupérer un post par son ID */
    suspend fun getPost(id: Int) = api.getPost(id)

    /** Récupérer les commentaires d'un post */
    suspend fun getComments(id: Int) = api.getComments(id)

    /** Créer un nouveau post (userId fixé à 1 pour la démo) */
    suspend fun createPost(title: String, body: String) =
        api.createPost(Post(userId = 1, title = title, body = body))

    /** Modifier un post existant (PATCH) */
    suspend fun updatePost(id: Int, title: String, body: String) =
        api.updatePost(id, mapOf("title" to title, "body" to body))

    /** Supprimer un post */
    suspend fun deletePost(id: Int) =
        api.deletePost(id).isSuccessful
}