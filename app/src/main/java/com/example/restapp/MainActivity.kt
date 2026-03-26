package com.example.restapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.restapp.ui.navigation.NavGraph
import com.example.restapp.ui.theme.RestAppTheme

/**
 * Point d'entrée de l'application.
 * Lance le thème Material3 et le graphe de navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestAppTheme {
                NavGraph()
            }
        }
    }
}