package com.example.s1_minutanutricional.data

import androidx.room.*
import com.example.s1_minutanutricional.model.Receta
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {

    @Query("SELECT * FROM recetas ORDER BY id ASC")
    fun obtenerTodas(): Flow<List<Receta>>

    @Query("SELECT * FROM recetas WHERE dia = :dia LIMIT 1")
    suspend fun obtenerPorDia(dia: String): Receta?

    @Query("SELECT * FROM recetas WHERE nombre LIKE '%' || :texto || '%' OR dia LIKE '%' || :texto || '%'")
    fun buscar(texto: String): Flow<List<Receta>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(receta: Receta)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodas(recetas: List<Receta>)

    @Update
    suspend fun actualizar(receta: Receta)

    @Delete
    suspend fun eliminar(receta: Receta)

    @Query("SELECT COUNT(*) FROM recetas")
    suspend fun contarRecetas(): Int
}
