package com.example.s1_minutanutricional

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class RecetaRobolectricTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("minuta_prefs", Context.MODE_PRIVATE)
    }

    // Guardar y leer el modo oscuro en SharedPreferences
    @Test
    fun sharedPreferences_guardarDarkMode_leeValorCorrecto() {
        // Guardar
        sharedPreferences.edit().putBoolean("dark_mode", true).apply()

        // Leer
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)

        assertTrue(darkMode)
    }

    //Valor por defecto de dark_mode es false
    @Test
    fun sharedPreferences_valorPorDefecto_esFalse() {
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)

        assertFalse(darkMode)
    }

    //Toggle del dark mode funciona correctamente
    @Test
    fun sharedPreferences_toggleDarkMode_cambiaValor() {
        // Empieza en false
        sharedPreferences.edit().putBoolean("dark_mode", false).apply()

        // Toggle
        val valorActual = sharedPreferences.getBoolean("dark_mode", false)
        sharedPreferences.edit().putBoolean("dark_mode", !valorActual).apply()

        // Debe ser true
        val nuevoValor = sharedPreferences.getBoolean("dark_mode", false)
        assertTrue(nuevoValor)
    }

    //El contexto de la aplicación no es null
    @Test
    fun contexto_noEsNull() {
        assertNotNull(context)
    }

    //El package name es correcto
    @Test
    fun contexto_packageName_esElCorrecto() {
        assertEquals("com.example.s1_minutanutricional", context.packageName)
    }
}
