package com.tumedidores.data.database

import androidx.room.TypeConverter
import com.tumedidores.data.entity.TipoMedicion
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTipoMedicion(value: String?): TipoMedicion? {
        return value?.let { TipoMedicion.valueOf(it) }
    }

    @TypeConverter
    fun tipoMedicionToString(tipo: TipoMedicion?): String? {
        return tipo?.name
    }
}