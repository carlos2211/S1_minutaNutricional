package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s1_minutanutricional.R
import com.example.s1_minutanutricional.ui.components.BotonPrincipal
import com.example.s1_minutanutricional.ui.theme.AccentGreenSoft
import com.example.s1_minutanutricional.ui.theme.DangerRed
import com.example.s1_minutanutricional.ui.theme.TextSecondary
import com.example.s1_minutanutricional.ui.viewmodel.RecetaViewModel
import com.google.firebase.auth.FirebaseAuth
@Composable
fun MenuSemanalScreen(
    navController: NavController,
    onToggleDarkMode: () -> Unit,
    isDarkMode: Boolean = false,
    recetaViewModel: RecetaViewModel = viewModel()
) {
    val textoBusqueda by recetaViewModel.textoBusqueda.collectAsState()
    val recetasFiltradas by recetaViewModel.recetasFiltradas.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo_home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.08f
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // darkMode - logout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // icono darkmode
                    IconButton(onClick = { onToggleDarkMode() }) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Modo claro" else "Modo oscuro",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = {

                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login") {
                                popUpTo("menu") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                // titulo
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Minuta semanal",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Tus recetas nutritivas de la semana",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }

                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))

                //filtro
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = { recetaViewModel.onBusquedaChange(it) },
                    label = { Text("Buscar receta") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    )
                )

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = AccentGreenSoft,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "  ${recetasFiltradas.size} resultado${if (recetasFiltradas.size != 1) "s" else ""}  ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                if (recetasFiltradas.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔍",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No se encontraron recetas",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    recetasFiltradas.forEach { receta ->
                        BotonPrincipal(
                            texto = receta.dia,
                            onClick = { navController.navigate("receta/${receta.dia}") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}