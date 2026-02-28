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

class RecetaViewModel(
    application: Application,
    private val repository: RecetaRepository,
    private val firebaseRepository: RecetaFirebaseRepository
) : AndroidViewModel(application) {

    //onstructor secundario
    constructor(application: Application) : this(
        application,
        RecetaRepository(
            RecetaDatabase.getDatabase(application).recetaDao()
        ),
        RecetaFirebaseRepository()
    )

    //Estado sincronización
    private val _sincronizando = MutableStateFlow(false)
    val sincronizando: StateFlow<Boolean> = _sincronizando

    private val _mensajeSync = MutableStateFlow("")
    val mensajeSync: StateFlow<String> = _mensajeSync

    //Texto búsqueda
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda

    //Recetas filtradas
    val recetasFiltradas: StateFlow<List<Receta>> =
        _textoBusqueda
            .debounce(300)
            .flatMapLatest { texto ->
                if (texto.isBlank()) {
                    repository.todasLasRecetas
                } else {
                    repository.buscar(texto)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    init {
        sincronizarDesdeFirebase()
    }

    fun onBusquedaChange(texto: String) {
        _textoBusqueda.value = texto
    }

    //Sincronizar Firebase → Room
    private fun sincronizarDesdeFirebase() {
        viewModelScope.launch {
            try {
                _sincronizando.value = true

                firebaseRepository.obtenerRecetas()
                    .collect { recetasFirebase ->

                        if (recetasFirebase.isNotEmpty()) {
                            recetasFirebase.forEach { receta ->
                                repository.insertar(receta)
                            }
                        } else {
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

    //Subir locales a Firebase
    private suspend fun subirRecetasLocalesAFirebase() {
        val recetasLocales = repository.todasLasRecetas.first()

        if (recetasLocales.isNotEmpty()) {
            firebaseRepository.guardarTodasLasRecetas(recetasLocales)
            _mensajeSync.value = "Recetas sincronizadas con Firebase"
        }
    }

    //CRUD

    fun insertar(receta: Receta) {
        viewModelScope.launch {
            repository.insertar(receta)
            try {
                firebaseRepository.guardarReceta(receta)
            } catch (_: Exception) {}
        }
    }

    fun actualizar(receta: Receta) {
        viewModelScope.launch {
            repository.actualizar(receta)
            try {
                firebaseRepository.guardarReceta(receta)
            } catch (_: Exception) {}
        }
    }

    fun eliminar(receta: Receta) {
        viewModelScope.launch {
            repository.eliminar(receta)
            try {
                firebaseRepository.eliminarReceta(receta)
            } catch (_: Exception) {}
        }
    }

    suspend fun obtenerPorDia(dia: String): Receta? {
        return repository.obtenerPorDia(dia)
    }
}