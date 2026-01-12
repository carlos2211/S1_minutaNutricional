package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.data.RecetasData

@Composable
fun RecetaDetalleScreen(
    dia: String,
    navController: NavController
) {

    val receta = RecetasData.recetas.find { it.dia == dia }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (receta != null) {

            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Ingredientes:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(receta.ingredientes)

            Text(
                text = "Preparación:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(receta.preparacion)

            Text(
                text = "Recomendación nutricional:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(receta.recomendacion)

        } else {
            Text("No se encontró la receta.")
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
