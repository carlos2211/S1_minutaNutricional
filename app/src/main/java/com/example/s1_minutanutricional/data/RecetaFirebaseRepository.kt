package com.example.s1_minutanutricional.data

import com.example.s1_minutanutricional.model.Receta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RecetaFirebaseRepository {

    // 🔹 Referencia a la base de datos Firebase
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // 🔹 Nodo raíz donde se guardan las recetas del usuario actual
    private val recetasRef
        get() = database.getReference("usuarios/${auth.currentUser?.uid}/recetas")

    // 🔹 Obtener todas las recetas en tiempo real como Flow
    fun obtenerRecetas(): Flow<List<Receta>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = snapshot.children.mapNotNull { child ->
                    child.getValue(Receta::class.java)?.copy(
                        id = child.key?.toIntOrNull() ?: 0
                    )
                }
                trySend(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        recetasRef.addValueEventListener(listener)

        // 🔹 Cancelar el listener cuando el Flow se cierra
        awaitClose { recetasRef.removeEventListener(listener) }
    }

    // 🔹 Subir una receta a Firebase
    suspend fun guardarReceta(receta: Receta) {
        recetasRef.child(receta.id.toString()).setValue(receta).await()
    }

    // 🔹 Subir lista de recetas (para poblar datos iniciales)
    suspend fun guardarTodasLasRecetas(recetas: List<Receta>) {
        recetas.forEach { receta ->
            recetasRef.child(receta.id.toString()).setValue(receta).await()
        }
    }

    // 🔹 Eliminar una receta de Firebase
    suspend fun eliminarReceta(receta: Receta) {
        recetasRef.child(receta.id.toString()).removeValue().await()
    }

    // 🔹 Verificar si ya hay recetas en Firebase (para no duplicar)
    suspend fun tieneRecetas(): Boolean {
        val snapshot = recetasRef.get().await()
        return snapshot.exists() && snapshot.childrenCount > 0
    }
}
