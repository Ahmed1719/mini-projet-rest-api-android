# 📱 Mini-Projet REST API — JSONPlaceholder — Compose + Retrofit

> **Matière** : Atelier développement mobile natif – Android Jetpack Compose  
> **Auteur** : NOM Prénom  
> **Date** : Janvier 2025

---

## 📋 Description

Application Android native développée avec **Jetpack Compose** qui consomme
l'API REST publique [JSONPlaceholder](https://jsonplaceholder.typicode.com/).

L'application permet de :
- ✅ **Lister** tous les posts (`GET /posts`)
- ✅ **Voir le détail** d'un post avec ses commentaires (`GET /posts/{id}`, `GET /posts/{id}/comments`)
- ✅ **Créer** un nouveau post (`POST /posts`)
- ✅ **Modifier** un post existant (`PATCH /posts/{id}`)
- ✅ **Supprimer** un post (`DELETE /posts/{id}`)

> ⚠️ **Note** : JSONPlaceholder est un service de test. Les opérations d'écriture
> (POST/PATCH/DELETE) renvoient des réponses valides mais ne persistent pas
> les données.

---

## 🏗️ Architecture

Le projet suit l'architecture **MVVM** (Model-View-ViewModel) :