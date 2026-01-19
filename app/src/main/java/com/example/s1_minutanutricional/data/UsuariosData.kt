package com.example.s1_minutanutricional.data

object UsuariosData {

    val usuarios = listOf(
        Usuario("usuario1@gmail.com", "1234"),
        Usuario("usuario2@gmail.com", "5678"),
        Usuario("usuario3@gmail.com", "abcd"),
        Usuario("usuario4@gmail.com", "2025"),
        Usuario("usuario5@gmail.com", "2026")
    )

    fun validarLogin(correo: String, password: String): Boolean {
        return usuarios.any {
            it.correo == correo && it.password == password
        }
    }
}
