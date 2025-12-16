package com.istea.worldcup

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.istea.worldcup.pages.detalle.DetallePage
import com.istea.worldcup.pages.grupos.GruposPage
import com.istea.worldcup.ui.theme.WorldcupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorldcupTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "grupos"
                ) {
                    //Pantalla lista de grupos
                    composable("grupos") {
                        GruposPage(
                            navHostController = navController,
                            context = this@MainActivity
                        )
                    }

                    //Pantalla detalle de un grupo
                    composable(
                        route = "detalle/{grupoId}",
                        arguments = listOf(
                            navArgument("grupoId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val grupoId = backStackEntry.arguments?.getString("grupoId") ?: ""
                        DetallePage(
                            navHostController = navController,
                            context = this@MainActivity,
                            groupID = grupoId // CORREGIDO: Usé la variable grupoId en lugar de "J"
                        )
                    }
                }
            }
        }
    }
}