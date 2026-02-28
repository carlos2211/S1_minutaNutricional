package com.example.s1_minutanutricional.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dia: String = "",
    val nombre: String = "",
    val ingredientes: String = "",
    val preparacion: String = "",
    val recomendacion: String = ""
)