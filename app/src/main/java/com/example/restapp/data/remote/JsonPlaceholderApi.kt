package com.example.restapp.data.remote

import com.example.restapp.data.model.Comment
import com.example.restapp.data.model.Post
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface Retrofit décrivant les endpoints de JSONPlaceholder.
 * Toutes les fonctions sont suspendues (coroutines).
 */
interface JsonPlaceholderApi {

    // Récupérer tous les posts
    @GET("posts")
    suspend fun getPosts(): List<Post>

    // Récupérer un post par son ID
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    // Récupérer les commentaires d'un post
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<Comment>

    // Créer un nouveau post
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post

    // Modifier partiellement un post (PATCH)
    @PATCH("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body patch: Map<String, Any>
    ): Post

    // Supprimer un post
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}