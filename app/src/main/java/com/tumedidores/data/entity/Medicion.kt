package com.tumedidores.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tumedidores.data.database.Converters
import java.util.Date
import java.util.UUID

@Entity(tableName = "mediciones")
@TypeConverters(Converters::class)
data class Medicion(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val tipo: TipoMedicion,
    val valor: Double,
    val fecha: Date,
    val observacion: String? = null
) {
    fun getIconResource(): String {
        return when (tipo) {
            TipoMedicion.AGUA -> "💧"
            TipoMedicion.LUZ -> "⚡"
            TipoMedicion.GAS -> "🔥"
        }
    }

    fun getColorResource(): Long {
        return when (tipo) {
            TipoMedicion.AGUA -> 0xFF2196F3
            TipoMedicion.LUZ -> 0xFFFFC107
            TipoMedicion.GAS -> 0xFFF44336
        }
    }
}

enum class TipoMedicion {
    AGUA, LUZ, GAS
}