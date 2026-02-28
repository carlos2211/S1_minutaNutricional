package com.example.s1_minutanutricional.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    // 🔹 Títulos de pantalla (ej: "Minuta semanal", nombre de receta)
    headlineMedium = TextStyle(
        fontSize    = 28.sp,
        fontWeight  = FontWeight.ExtraBold,
        lineHeight  = 34.sp,
        letterSpacing = (-0.5).sp
    ),

    headlineSmall = TextStyle(
        fontSize    = 22.sp,
        fontWeight  = FontWeight.Bold,
        lineHeight  = 28.sp
    ),

    // 🔹 Subtítulos (ej: "Ingredientes:", "Preparación:")
    titleMedium = TextStyle(
        fontSize    = 18.sp,
        fontWeight  = FontWeight.Bold,
        lineHeight  = 24.sp,
        letterSpacing = 0.1.sp
    ),

    titleSmall = TextStyle(
        fontSize    = 16.sp,
        fontWeight  = FontWeight.SemiBold,
        lineHeight  = 22.sp
    ),

    // 🔹 Cuerpo de texto (ej: ingredientes, preparación)
    bodyLarge = TextStyle(
        fontSize    = 16.sp,
        fontWeight  = FontWeight.Normal,
        lineHeight  = 24.sp
    ),

    bodyMedium = TextStyle(
        fontSize    = 14.sp,
        fontWeight  = FontWeight.Normal,
        lineHeight  = 20.sp
    ),

    bodySmall = TextStyle(
        fontSize    = 12.sp,
        fontWeight  = FontWeight.Normal,
        lineHeight  = 18.sp
    ),

    // 🔹 Etiquetas (ej: botones, labels)
    labelLarge = TextStyle(
        fontSize    = 16.sp,
        fontWeight  = FontWeight.Bold,
        letterSpacing = 0.5.sp
    ),

    labelMedium = TextStyle(
        fontSize    = 13.sp,
        fontWeight  = FontWeight.Medium,
        letterSpacing = 0.4.sp
    )
)