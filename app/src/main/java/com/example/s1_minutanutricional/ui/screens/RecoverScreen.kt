package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.ui.theme.PrimaryBlue
//Firebase Auth
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecoverScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var esExito by remember { mutableStateOf(false) }

    //Instancia de Firebase Auth
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Recuperar contraseña",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Ingresa tu correo y te enviaremos un enlace para restablecer tu contraseña.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                if (email.isBlank()) {
                    mensaje = "Ingresa tu correo electrónico"
                    return@Button
                }

                isLoading = true
                mensaje = ""

                //enviar email de recuperación
                auth.sendPasswordResetEmail(email.trim())
                    .addOnSuccessListener {
                        isLoading = false
                        esExito = true
                        mensaje = "✅ Correo enviado. Revisa tu bandeja de entrada."
                    }
                    .addOnFailureListener { exception ->
                        isLoading = false
                        esExito = false
                        mensaje = when {
                            exception.message?.contains("no user record") == true ->
                                "No existe una cuenta con ese correo"
                            exception.message?.contains("badly formatted") == true ->
                                "Formato de correo inválido"
                            else -> "Error al enviar. Intenta de nuevo"
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
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
                Text("Enviar", style = MaterialTheme.typography.labelLarge)
            }
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = if (esExito) Color(0xFF2E7D32) else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver al inicio", style = MaterialTheme.typography.labelLarge)
        }
    }
}