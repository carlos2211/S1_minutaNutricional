package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuSemanalScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .padding(top = 50.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Minuta semanal",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { navController.navigate("receta/Lunes") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lunes")
        }

        Button(
            onClick = { navController.navigate("receta/Martes") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Martes")
        }

        Button(
            onClick = { navController.navigate("receta/Miércoles") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Miércoles")
        }
        Button(
            onClick = { navController.navigate("receta/Jueves") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Jueves")
        }
        Button(
            onClick = { navController.navigate("receta/Viernes") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Viernes")
        }
        Button(
            onClick = { navController.navigate("receta/Sábado") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sábado")
        }
        Button(
            onClick = { navController.navigate("receta/Domingo") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Domingo")
        }
    }
}
