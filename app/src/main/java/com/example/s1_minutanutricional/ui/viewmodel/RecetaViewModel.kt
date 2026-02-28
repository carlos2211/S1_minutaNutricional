package com.example.s1_minutanutricional.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.s1_minutanutricional.data.RecetaDatabase
import com.example.s1_minutanutricional.data.RecetaFirebaseRepository
import com.example.s1_minutanutricional.data.RecetaRepository
import com.example.s1_minutanutricional.model.Receta
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecetaRepository
    private val firebaseRepository = RecetaFirebaseRepository()

    // 🔹 Estado de sincronización con Firebase
    private val _sincronizando = MutableStateFlow(false)
    val sincronizando: StateFlow<Boolean> = _sincronizando

    private val _mensajeSync = MutableStateFlow("")
    val mensajeSync: StateFlow<String> = _mensajeSync

    // 🔹 Texto de búsqueda
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda

    // 🔹 Recetas filtradas desde Room (fuente local)
    val recetasFiltradas: StateFlow<List<Receta>>

    init {
        val dao = RecetaDatabase.getDatabase(application).recetaDao()
        repository = RecetaRepository(dao)

        recetasFiltradas = _textoBusqueda
            .debounce(300)
            .flatMapLatest { texto ->
                if (texto.isBlank()) repository.todasLasRecetas
                else repository.buscar(texto)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

        // 🔹 Al iniciar, sincronizar Firebase → Room
        sincronizarDesdeFirebase()
    }

    fun onBusquedaChange(texto: String) {
        _textoBusqueda.value = texto
    }

    // 🔹 Sincronizar recetas desde Firebase a Room local
    private fun sincronizarDesdeFirebase() {
        viewModelScope.launch {
            try {
                _sincronizando.value = true

                firebaseRepository.obtenerRecetas()
                    .collect { recetasFirebase ->
                        if (recetasFirebase.isNotEmpty()) {
                            // 🔹 Firebase tiene datos → guardar en Room local
                            recetasFirebase.forEach { receta ->
                                repository.insertar(receta)
                            }
                        } else {
                            // 🔹 Firebase vacío → subir recetas locales a Firebase
                            subirRecetasLocalesAFirebase()
                        }
                        _sincronizando.value = false
                    }

            } catch (e: Exception) {
                _sincronizando.value = false
                _mensajeSync.value = "Sin conexión - usando datos locales"
            }
        }
    }

    // 🔹 Subir recetas de Room a Firebase (primera vez)
    private suspend fun subirRecetasLocalesAFirebase() {
        val recetasLocales = repository.todasLasRecetas.first()
        if (recetasLocales.isNotEmpty()) {
            firebaseRepository.guardarTodasLasRecetas(recetasLocales)
            _mensajeSync.value = "✅ Recetas sincronizadas con Firebase"
        }
    }

    // 🔹 Insertar receta en Room Y Firebase
    fun insertar(receta: Receta) {
        viewModelScope.launch {
            repository.insertar(receta)
            try { firebaseRepository.guardarReceta(receta) } catch (_: Exception) {}
        }
    }

    // 🔹 Actualizar receta en Room Y Firebase
    fun actualizar(receta: Receta) {
        viewModelScope.launch {
            repository.actualizar(receta)
            try { firebaseRepository.guardarReceta(receta) } catch (_: Exception) {}
        }
    }

    // 🔹 Eliminar receta en Room Y Firebase
    fun eliminar(receta: Receta) {
        viewModelScope.launch {
            repository.eliminar(receta)
            try { firebaseRepository.eliminarReceta(receta) } catch (_: Exception) {}
        }
    }

    suspend fun obtenerPorDia(dia: String): Receta? {
        return repository.obtenerPorDia(dia)
    }
}