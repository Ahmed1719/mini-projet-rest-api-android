package com.example.restapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapp.data.model.Comment
import com.example.restapp.data.model.Post
import com.example.restapp.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel partagé entre les écrans.
 * Gère les états de la liste, du détail, des commentaires
 * et les opérations CRUD sur les posts.
 */
class PostViewModel : ViewModel() {

    private val repo = PostRepository()

    // ── État : liste des posts ──────────────────────────
    private val _posts = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val posts: StateFlow<UiState<List<Post>>> = _posts

    // ── État : détail d'un post ─────────────────────────
    private val _postDetail = MutableStateFlow<UiState<Post>>(UiState.Loading)
    val postDetail: StateFlow<UiState<Post>> = _postDetail

    // ── État : commentaires d'un post ───────────────────
    private val _comments = MutableStateFlow<UiState<List<Comment>>>(UiState.Loading)
    val comments: StateFlow<UiState<List<Comment>>> = _comments

    /** Charger tous les posts (GET /posts) */
    fun loadPosts() {
        viewModelScope.launch {
            _posts.value = UiState.Loading
            try {
                val result = repo.getPosts()
                _posts.value = UiState.Success(result)
            } catch (e: Exception) {
                _posts.value = UiState.Error(e.localizedMessage)
            }
        }
    }

    /** Charger un post par ID (GET /posts/{id}) */
    fun loadPostDetail(id: Int) {
        viewModelScope.launch {
            _postDetail.value = UiState.Loading
            try {
                val result = repo.getPost(id)
                _postDetail.value = UiState.Success(result)
            } catch (e: Exception) {
                _postDetail.value = UiState.Error(e.localizedMessage)
            }
        }
    }

    /** Charger les commentaires (GET /posts/{id}/comments) */
    fun loadComments(postId: Int) {
        viewModelScope.launch {
            _comments.value = UiState.Loading
            try {
                val result = repo.getComments(postId)
                _comments.value = UiState.Success(result)
            } catch (e: Exception) {
                _comments.value = UiState.Error(e.localizedMessage)
            }
        }
    }

    /** Créer un post (POST /posts) */
    fun createPost(title: String, body: String) {
        viewModelScope.launch {
            try {
                repo.createPost(title, body)
                loadPosts() // Rafraîchir la liste
            } catch (_: Exception) { }
        }
    }

    /** Modifier un post (PATCH /posts/{id}) */
    fun updatePost(id: Int, title: String, body: String) {
        viewModelScope.launch {
            try {
                repo.updatePost(id, title, body)
                loadPosts() // Rafraîchir la liste
            } catch (_: Exception) { }
        }
    }

    /** Supprimer un post (DELETE /posts/{id}) */
    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                repo.deletePost(id)
                loadPosts() // Rafraîchir la liste
            } catch (_: Exception) { }
        }
    }
}