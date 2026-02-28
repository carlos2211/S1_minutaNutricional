package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.ui.theme.PrimaryBlue
// 🔹 Firebase Auth
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var esExito by remember { mutableStateOf(false) }

    // 🔹 Instancia de Firebase Auth
    val auth = FirebaseAuth.getInstance()

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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                // 🔹 Validaciones locales antes de llamar a Firebase
                mensaje = when {
                    correo.isBlank() || password.isBlank() ->
                        "Todos los campos son obligatorios"
                    password != confirmarPassword ->
                        "Las contraseñas no coinciden"
                    password.length < 6 ->
                        "La contraseña debe tener al menos 6 caracteres"
                    else -> ""
                }

                if (mensaje.isNotEmpty()) return@Button

                isLoading = true

                // 🔹 Firebase Auth: crear usuario con email y password
                auth.createUserWithEmailAndPassword(correo.trim(), password)
                    .addOnSuccessListener {
                        isLoading = false
                        esExito = true
                        mensaje = "✅ Usuario registrado correctamente"
                    }
                    .addOnFailureListener { exception ->
                        isLoading = false
                        esExito = false
                        mensaje = when {
                            exception.message?.contains("already in use") == true ->
                                "Este correo ya está registrado"
                            exception.message?.contains("badly formatted") == true ->
                                "Formato de correo inválido"
                            else -> "Error al registrar. Intenta de nuevo"
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Registrar",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = if (esExito) Color(0xFF2E7D32) else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // 🔹 Si se registró bien, mostrar botón para ir al login
        if (esExito) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Ir al Login", style = MaterialTheme.typography.labelLarge)
            }
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text(text = "Volver", style = MaterialTheme.typography.labelLarge)
        }
    }
}