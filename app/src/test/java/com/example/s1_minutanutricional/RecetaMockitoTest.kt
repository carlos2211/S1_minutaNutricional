package com.example.s1_minutanutricional

import org.junit.Assert.*
import org.junit.Test

class RecetaValidadorTest {

    private val validador = RecetaValidador()

    //Nombre válido pasa la validación
    @Test
    fun validarNombre_nombreValido_retornaTrue() {
        val resultado = validador.nombreEsValido("Ensalada de pollo")
        assertTrue(resultado)
    }

    //Nombre vacío no es válido
    @Test
    fun validarNombre_nombreVacio_retornaFalse() {
        val resultado = validador.nombreEsValido("")
        assertFalse(resultado)
    }

    //Nombre muy corto no es válido (menos de 3 caracteres)
    @Test
    fun validarNombre_nombreMuyCorto_retornaFalse() {
        val resultado = validador.nombreEsValido("Ab")
        assertFalse(resultado)
    }

    //Ingredientes válidos pasan la validación
    @Test
    fun validarIngredientes_ingredientesValidos_retornaTrue() {
        val resultado = validador.ingredientesEsValido("Pollo, lechuga, tomate")
        assertTrue(resultado)
    }

    //Ingredientes vacíos no son válidos
    @Test
    fun validarIngredientes_vacio_retornaFalse() {
        val resultado = validador.ingredientesEsValido("")
        assertFalse(resultado)
    }

    //Día válido (dentro de los 7 días)
    @Test
    fun validarDia_diaValido_retornaTrue() {
        val resultado = validador.diaEsValido("Lunes")
        assertTrue(resultado)
    }

    //Día inválido retorna false
    @Test
    fun validarDia_diaInvalido_retornaFalse() {
        val resultado = validador.diaEsValido("Funday")
        assertFalse(resultado)
    }

    //Receta completa es válida
    @Test
    fun validarReceta_completa_esValida() {
        val resultado = validador.recetaEsValida(
            dia = "Lunes",
            nombre = "Ensalada de pollo",
            ingredientes = "Pollo, lechuga",
            preparacion = "Mezclar todo."
        )
        assertTrue(resultado)
    }

    //Receta incompleta no es válida
    @Test
    fun validarReceta_sinPreparacion_esInvalida() {
        val resultado = validador.recetaEsValida(
            dia = "Lunes",
            nombre = "Ensalada",
            ingredientes = "Lechuga",
            preparacion = ""
        )
        assertFalse(resultado)
    }
}

class RecetaValidador {

    private val diasValidos = listOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )

    fun nombreEsValido(nombre: String): Boolean =
        nombre.isNotBlank() && nombre.length >= 3

    fun ingredientesEsValido(ingredientes: String): Boolean =
        ingredientes.isNotBlank()

    fun diaEsValido(dia: String): Boolean =
        dia in diasValidos

    fun recetaEsValida(
        dia: String,
        nombre: String,
        ingredientes: String,
        preparacion: String
    ): Boolean =
        diaEsValido(dia) &&
                nombreEsValido(nombre) &&
                ingredientesEsValido(ingredientes) &&
                preparacion.isNotBlank()
}