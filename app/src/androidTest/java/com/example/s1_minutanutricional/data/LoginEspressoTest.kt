package com.example.s1_minutanutricional

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // El campo de correo es visible al iniciar
    @Test
    fun loginScreen_campoCorreo_esVisible() {
        onView(withHint("Correo"))
            .check(matches(isDisplayed()))
    }

    //El campo de contraseña es visible al iniciar
    @Test
    fun loginScreen_campoPassword_esVisible() {
        onView(withHint("Contraseña"))
            .check(matches(isDisplayed()))
    }

    //El btn Ingresar está visible y habilitado
    @Test
    fun loginScreen_botonIngresar_estaHabilitado() {
        onView(withText("Ingresar"))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }

    //Ingresar credenciales incorrectas muestra error
    @Test
    fun loginScreen_credencialesIncorrectas_muestraError() {
        onView(withHint("Correo"))
            .perform(typeText("noexiste@test.com"), closeSoftKeyboard())

        onView(withHint("Contraseña"))
            .perform(typeText("wrongpass"), closeSoftKeyboard())

        onView(withText("Ingresar"))
            .perform(click())

        onView(withText("Error al iniciar sesión. Intenta de nuevo"))
            .check(matches(isDisplayed()))
    }

    //el btn Registrarse es visible
    @Test
    fun loginScreen_botonRegistrarse_esVisible() {
        onView(withText("Registrarse"))
            .check(matches(isDisplayed()))
    }

    //El btn ¿Olvidó su contraseña? es visible
    @Test
    fun loginScreen_botonRecuperar_esVisible() {
        onView(withText("¿Olvidó su contraseña?"))
            .check(matches(isDisplayed()))
    }
}
