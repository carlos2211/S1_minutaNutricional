package com.example.s1_minutanutricional

// 📁 Guardar en: src/test/java/com/example/s1_minutanutricional/RecetaTest.kt

import com.example.s1_minutanutricional.model.Receta
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RecetaTest {

    // 🔹 Lista de recetas de prueba (se inicializa antes de cada test)
    private lateinit var recetas: List<Receta>

    @Before
    fun setUp() {
        recetas = listOf(
            Receta(
                id = 1,
                dia = "Lunes",
                nombre = "Ensalada de pollo",
                ingredientes = "Pollo cocido, lechuga, tomate",
                preparacion = "Cocer el pollo y mezclar.",
                recomendacion = "Alta en proteínas."
            ),
            Receta(
                id = 2,
                dia = "Martes",
                nombre = "Pescado al horno",
                ingredientes = "Merluza, limón, ajo",
                preparacion = "Hornear 20 minutos.",
                recomendacion = "Rico en Omega 3."
            ),
            Receta(
                id = 3,
                dia = "Miércoles",
                nombre = "Lentejas",
                ingredientes = "Lentejas, zanahoria, cebolla",
                preparacion = "Cocer con verduras.",
                recomendacion = "Fuente de hierro."
            )
        )
    }

    // ✅ TEST 1: Verificar que el modelo Receta se crea correctamente
    @Test
    fun receta_creadaCorrectamente() {
        val receta = Receta(
            id = 1,
            dia = "Lunes",
            nombre = "Ensalada de pollo",
            ingredientes = "Pollo, lechuga",
            preparacion = "Mezclar todo.",
            recomendacion = "Saludable."
        )

        assertEquals("Lunes", receta.dia)
        assertEquals("Ensalada de pollo", receta.nombre)
        assertNotNull(receta.ingredientes)
    }

    // ✅ TEST 2: Verificar que los valores por defecto son cadenas vacías
    @Test
    fun receta_valoresPorDefecto_sonVacios() {
        val receta = Receta()

        assertEquals("", receta.dia)
        assertEquals("", receta.nombre)
        assertEquals("", receta.ingredientes)
        assertEquals(0, receta.id)
    }

    // ✅ TEST 3: Filtrar recetas por nombre
    @Test
    fun filtrarRecetas_porNombre_retornaResultadosCorrecto() {
        val resultado = recetas.filter {
            it.nombre.contains("pollo", ignoreCase = true)
        }

        assertEquals(1, resultado.size)
        assertEquals("Ensalada de pollo", resultado[0].nombre)
    }

    // ✅ TEST 4: Filtrar recetas por día
    @Test
    fun filtrarRecetas_porDia_retornaResultadoCorrecto() {
        val resultado = recetas.filter {
            it.dia.contains("Martes", ignoreCase = true)
        }

        assertEquals(1, resultado.size)
        assertEquals("Pescado al horno", resultado[0].nombre)
    }

    // ✅ TEST 5: Búsqueda vacía retorna todas las recetas
    @Test
    fun filtrarRecetas_busquedaVacia_retornaTodas() {
        val textoBusqueda = ""
        val resultado = if (textoBusqueda.isBlank()) recetas
                        else recetas.filter { it.nombre.contains(textoBusqueda) }

        assertEquals(3, resultado.size)
    }

    // ✅ TEST 6: Búsqueda sin resultados retorna lista vacía
    @Test
    fun filtrarRecetas_sinCoincidencias_retornaListaVacia() {
        val resultado = recetas.filter {
            it.nombre.contains("sushi", ignoreCase = true)
        }

        assertTrue(resultado.isEmpty())
    }

    // ✅ TEST 7: Filtro es case-insensitive (mayúsculas/minúsculas)
    @Test
    fun filtrarRecetas_caseInsensitive_funcionaCorrectamente() {
        val resultadoMinusculas = recetas.filter {
            it.nombre.contains("ensalada", ignoreCase = true)
        }
        val resultadoMayusculas = recetas.filter {
            it.nombre.contains("ENSALADA", ignoreCase = true)
        }

        assertEquals(resultadoMinusculas.size, resultadoMayusculas.size)
    }

    // ✅ TEST 8: Verificar que cada receta tiene día único
    @Test
    fun recetas_diasSonUnicos() {
        val dias = recetas.map { it.dia }
        val diasUnicos = dias.toSet()

        assertEquals(dias.size, diasUnicos.size)
    }

    // ✅ TEST 9: Ningún campo obligatorio está vacío en las recetas de prueba
    @Test
    fun recetas_camposObligatorios_noEstanVacios() {
        recetas.forEach { receta ->
            assertTrue("Día vacío en ${receta.id}", receta.dia.isNotBlank())
            assertTrue("Nombre vacío en ${receta.id}", receta.nombre.isNotBlank())
            assertTrue("Ingredientes vacíos en ${receta.id}", receta.ingredientes.isNotBlank())
            assertTrue("Preparación vacía en ${receta.id}", receta.preparacion.isNotBlank())
        }
    }

    // ✅ TEST 10: Verificar búsqueda combinada por nombre O día
    @Test
    fun filtrarRecetas_porNombreODia_retornaAmbos() {
        val textoBusqueda = "Miércoles"
        val resultado = recetas.filter {
            it.nombre.contains(textoBusqueda, ignoreCase = true) ||
            it.dia.contains(textoBusqueda, ignoreCase = true)
        }

        assertEquals(1, resultado.size)
        assertEquals("Lentejas", resultado[0].nombre)
    }
}
