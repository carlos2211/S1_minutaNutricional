package com.example.s1_minutanutricional.data

import com.example.s1_minutanutricional.model.Receta
import kotlinx.coroutines.flow.Flow

class RecetaRepository(private val dao: RecetaDao) {

    // Obtener todas las recetas
    val todasLasRecetas: Flow<List<Receta>> = dao.obtenerTodas()

    //Buscar recetas por texto
    fun buscar(texto: String): Flow<List<Receta>> = dao.buscar(texto)

    // Obtener una receta por día
    suspend fun obtenerPorDia(dia: String): Receta? = dao.obtenerPorDia(dia)

    // Insertar una receta
    suspend fun insertar(receta: Receta) = dao.insertar(receta)

    // Actualizar una receta
    suspend fun actualizar(receta: Receta) = dao.actualizar(receta)

    // Eliminar una receta
    suspend fun eliminar(receta: Receta) = dao.eliminar(receta)
}
