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

    //base de datos Firebase
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val recetasRef
        get() = database.getReference("usuarios/${auth.currentUser?.uid}/recetas")

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

        awaitClose { recetasRef.removeEventListener(listener) }
    }

    suspend fun guardarReceta(receta: Receta) {
        recetasRef.child(receta.id.toString()).setValue(receta).await()
    }

    suspend fun guardarTodasLasRecetas(recetas: List<Receta>) {
        recetas.forEach { receta ->
            recetasRef.child(receta.id.toString()).setValue(receta).await()
        }
    }

    suspend fun eliminarReceta(receta: Receta) {
        recetasRef.child(receta.id.toString()).removeValue().await()
    }

    suspend fun tieneRecetas(): Boolean {
        val snapshot = recetasRef.get().await()
        return snapshot.exists() && snapshot.childrenCount > 0
    }
}
