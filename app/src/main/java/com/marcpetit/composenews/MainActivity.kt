package com.marcpetit.composenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcpetit.composenews.Destinations.DETAIL_SCREEN
import com.marcpetit.composenews.Destinations.LIST_SCREEN
import com.marcpetit.composenews.ui.theme.ComposeNewsTheme
import com.marcpetit.composenews.ui.views.DetailScreen
import com.marcpetit.composenews.ui.views.ListScreen
import dagger.hilt.android.AndroidEntryPoint

object Destinations {
    const val LIST_SCREEN = "LIST_SCREEN"
    const val DETAIL_SCREEN = "DETAIL_SCREEN"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LIST_SCREEN,
                    ) {
                        composable(LIST_SCREEN) {
                            ListScreen(navController)
                        }
                        composable("$DETAIL_SCREEN/{newsTitle}") {
                            it.arguments?.getString("newsTitle")?.let { title ->
                                DetailScreen(title, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
