package com.example.s1_minutanutricional.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.s1_minutanutricional.model.Receta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Receta::class], version = 1, exportSchema = false)
abstract class RecetaDatabase : RoomDatabase() {

    abstract fun recetaDao(): RecetaDao

    companion object {

        @Volatile
        private var INSTANCE: RecetaDatabase? = null

        fun getDatabase(context: Context): RecetaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetaDatabase::class.java,
                    "receta_database"
                )
                    .addCallback(RecetaCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    //Callback para poblar la DB con datos iniciales al crearla por primera vez
    private class RecetaCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    poblarDatosIniciales(database.recetaDao())
                }
            }
        }

        suspend fun poblarDatosIniciales(dao: RecetaDao) {
            val recetasIniciales = listOf(
                Receta(
                    dia = "Lunes",
                    nombre = "Ensalada de pollo",
                    ingredientes = "Pollo cocido, lechuga, tomate, aceite de oliva",
                    preparacion = "Cocer el pollo, cortar los ingredientes y mezclar.",
                    recomendacion = "Alta en proteínas y baja en grasas."
                ),
                Receta(
                    dia = "Martes",
                    nombre = "Pescado al horno",
                    ingredientes = "Merluza, limón, ajo",
                    preparacion = "Hornear el pescado durante 20 minutos.",
                    recomendacion = "Rico en Omega 3."
                ),
                Receta(
                    dia = "Miércoles",
                    nombre = "Lentejas",
                    ingredientes = "Lentejas, zanahoria, cebolla",
                    preparacion = "Cocer las lentejas con verduras.",
                    recomendacion = "Fuente de hierro y fibra."
                ),
                Receta(
                    dia = "Jueves",
                    nombre = "Tortilla de verduras",
                    ingredientes = "Huevos, zapallo italiano, cebolla",
                    preparacion = "Saltear verduras y mezclar con huevos.",
                    recomendacion = "Aporta proteínas y vitaminas."
                ),
                Receta(
                    dia = "Viernes",
                    nombre = "Pasta integral",
                    ingredientes = "Pasta integral, salsa de tomate",
                    preparacion = "Cocer la pasta y servir con salsa.",
                    recomendacion = "Entrega energía de larga duración."
                ),
                Receta(
                    dia = "Sábado",
                    nombre = "Pastel de choclo",
                    ingredientes = "Choclos, albahaca, carne molida, cebolla",
                    preparacion = "Cocer los choclos, y mezclar con el pino.",
                    recomendacion = "Comida alta en fibra."
                ),
                Receta(
                    dia = "Domingo",
                    nombre = "Arroz con carne mechada",
                    ingredientes = "Choclillo, verduras, arroz",
                    preparacion = "Sellar la carne, luego aplicar vino, y llenar con agua por 1 hora.",
                    recomendacion = "Entrega mucha proteína y rico sabor."
                )
            )
            dao.insertarTodas(recetasIniciales)
        }
    }
}
