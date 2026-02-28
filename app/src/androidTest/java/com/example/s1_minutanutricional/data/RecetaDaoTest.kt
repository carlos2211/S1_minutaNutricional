package com.example.s1_minutanutricional.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.s1_minutanutricional.model.Receta
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecetaDaoTest {

    private lateinit var database: RecetaDatabase
    private lateinit var dao: RecetaDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            RecetaDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.recetaDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertarYObtenerTodas_funcionaCorrectamente() = runBlocking {

        val receta = Receta(
            id = 0,
            dia = "Lunes",
            nombre = "Test",
            ingredientes = "x",
            preparacion = "x",
            recomendacion = "x"
        )

        dao.insertar(receta)

        val lista = dao.obtenerTodas().first()

        assertEquals(1, lista.size)
        assertEquals("Test", lista[0].nombre)
    }

    @Test
    fun contarRecetas_funcionaCorrectamente() = runBlocking {

        dao.insertar(
            Receta(
                id = 0,
                dia = "Lunes",
                nombre = "A",
                ingredientes = "x",
                preparacion = "x",
                recomendacion = "x"
            )
        )

        dao.insertar(
            Receta(
                id = 0,
                dia = "Martes",
                nombre = "B",
                ingredientes = "x",
                preparacion = "x",
                recomendacion = "x"
            )
        )

        val total = dao.contarRecetas()

        assertEquals(2, total)
    }

    @Test
    fun eliminar_funcionaCorrectamente() = runBlocking {

        val receta = Receta(
            id = 0,
            dia = "Lunes",
            nombre = "Eliminar",
            ingredientes = "x",
            preparacion = "x",
            recomendacion = "x"
        )

        dao.insertar(receta)

        val guardada = dao.obtenerPorDia("Lunes")!!
        dao.eliminar(guardada)

        val total = dao.contarRecetas()

        assertEquals(0, total)
    }
}