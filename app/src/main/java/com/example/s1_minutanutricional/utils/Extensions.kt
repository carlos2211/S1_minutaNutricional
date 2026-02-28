package com.example.s1_minutanutricional.utils

fun String.capitalizarPrimera(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}