package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.data.Usuario
import com.example.s1_minutanutricional.data.UsuariosData
import com.example.s1_minutanutricional.ui.theme.PrimaryBlue

@Composable
fun RegisterScreen(navController: NavController) {

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Registro de usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                mensaje = when {
                    correo.isBlank() || password.isBlank() ->
                        "Todos los campos son obligatorios"

                    password != confirmarPassword ->
                        "Las contraseñas no coinciden"

                    else -> {
                        val nuevoUsuario = Usuario(correo, password)
                        UsuariosData.usuarios.add(nuevoUsuario)
                        "Usuario registrado correctamente"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )

        ) {
            Text(
                text = "Registrar",
                style = MaterialTheme.typography.labelLarge
            )
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = if (mensaje.contains("correctamente")) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        TextButton(
            onClick = { navController.popBackStack() }
        ) {
            Text(
                text = "Volver",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
