package com.example.s1_minutanutricional

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.s1_minutanutricional.ui.navigation.AppNavigation
import com.example.s1_minutanutricional.ui.theme.S1MinutaNutricionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("minuta_prefs", Context.MODE_PRIVATE)
        val darkModeGuardado = prefs.getBoolean("dark_mode", false)

        setContent {

            var darkMode by remember { mutableStateOf(darkModeGuardado) }

            S1MinutaNutricionalTheme(
                darkTheme = darkMode
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        darkMode = darkMode,
                        onToggleDarkMode = {
                            darkMode = !darkMode

                            prefs.edit().putBoolean("dark_mode", darkMode).apply()
                        }
                    )
                }
            }
        }
    }
}