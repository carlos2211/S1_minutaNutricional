package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.R
import com.example.s1_minutanutricional.ui.components.BotonPrincipal


@Composable
fun MenuSemanalScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {


        Image(
            painter = painterResource(id = R.drawable.fondo_home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.25f
        )


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Minuta semanal",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(1f),
                        color = Color.Black
                    )

                    Button(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("menu") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F) // Rojo accesible
                        ),
                        modifier = Modifier
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botones de días
                val dias = listOf(
                    "Lunes", "Martes", "Miércoles",
                    "Jueves", "Viernes", "Sábado", "Domingo"
                )

                dias.forEach { dia ->
                    BotonPrincipal(
                        texto = dia,
                        onClick = { navController.navigate("receta/$dia") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
