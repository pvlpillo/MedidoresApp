package com.tumedidores.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumedidores.data.entity.Medicion
import com.tumedidores.data.entity.TipoMedicion
import com.tumedidores.data.repository.MedicionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

data class MedicionUiState(
    val mediciones: List<Medicion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val tipoSeleccionado: TipoMedicion = TipoMedicion.AGUA,
    val valor: String = "",
    val observacion: String = "",
    val fechaSeleccionada: Date = Date()
)

class MedicionViewModel(private val repository: MedicionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MedicionUiState())
    val uiState: StateFlow<MedicionUiState> = _uiState.asStateFlow()

    init {
        cargarMediciones()
    }

    private fun cargarMediciones() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.allMediciones.collect { mediciones ->
                    _uiState.update { it.copy(mediciones = mediciones, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun agregarMedicion() {
        val valor = _uiState.value.valor.toDoubleOrNull()
        if (valor == null || valor <= 0) {
            _uiState.update { it.copy(error = "Ingrese un valor válido") }
            return
        }

        viewModelScope.launch {
            try {
                val nuevaMedicion = Medicion(
                    tipo = _uiState.value.tipoSeleccionado,
                    valor = valor,
                    fecha = _uiState.value.fechaSeleccionada,
                    observacion = _uiState.value.observacion.ifEmpty { null }
                )
                repository.insert(nuevaMedicion)
                limpiarFormulario()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al guardar: ${e.message}") }
            }
        }
    }

    fun eliminarMedicion(medicion: Medicion) {
        viewModelScope.launch {
            try {
                repository.delete(medicion)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar: ${e.message}") }
            }
        }
    }

    fun actualizarTipo(tipo: TipoMedicion) {
        _uiState.update { it.copy(tipoSeleccionado = tipo) }
    }

    fun actualizarValor(valor: String) {
        _uiState.update { it.copy(valor = valor) }
    }

    fun actualizarObservacion(observacion: String) {
        _uiState.update { it.copy(observacion = observacion) }
    }

    fun actualizarFecha(fecha: Date) {
        _uiState.update { it.copy(fechaSeleccionada = fecha) }
    }

    fun limpiarError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun limpiarFormulario() {
        _uiState.update {
            it.copy(
                valor = "",
                observacion = "",
                error = null
            )
        }
    }
}