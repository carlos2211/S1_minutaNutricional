package com.example.s1_minutanutricional.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.s1_minutanutricional.R
import com.example.s1_minutanutricional.ui.theme.PrimaryBlue
// 🔹 Firebase Auth
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // 🔹 Instancia de Firebase Auth
    val auth = FirebaseAuth.getInstance()

    // 🔹 Si ya hay sesión activa, ir directo al menú
    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            navController.navigate("menu") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
        )

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(max = 360.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Ingreso",
                    style = MaterialTheme.typography.headlineMedium
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
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

                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Ingresa correo y contraseña"
                            return@Button
                        }

                        isLoading = true
                        errorMessage = ""

                        // 🔹 Firebase Auth: iniciar sesión con email y password
                        auth.signInWithEmailAndPassword(email.trim(), password)
                            .addOnSuccessListener {
                                isLoading = false
                                navController.navigate("menu") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                            .addOnFailureListener { exception ->
                                isLoading = false
                                errorMessage = when {
                                    exception.message?.contains("no user record") == true ->
                                        "No existe una cuenta con ese correo"
                                    exception.message?.contains("password is invalid") == true ->
                                        "Contraseña incorrecta"
                                    exception.message?.contains("badly formatted") == true ->
                                        "Formato de correo inválido"
                                    else -> "Error al iniciar sesión. Intenta de nuevo"
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
                            text = "Ingresar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextButton(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Registrarse", fontSize = 26.sp)
                }

                TextButton(
                    onClick = { navController.navigate("recover") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("¿Olvidó su contraseña?", fontSize = 26.sp)
                }
            }
        }
    }
}