package com.tumedidores.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.tumedidores.data.entity.Medicion
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {
    @Insert
    suspend fun insert(medicion: Medicion)

    @Update
    suspend fun update(medicion: Medicion)

    @Delete
    suspend fun delete(medicion: Medicion)

    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    fun getAllMediciones(): Flow<List<Medicion>>

    @Query("SELECT * FROM mediciones WHERE id = :id")
    suspend fun getMedicionById(id: String): Medicion?
}

class MedicionRepository(private val medicionDao: MedicionDao) {
    val allMediciones: Flow<List<Medicion>> = medicionDao.getAllMediciones()

    suspend fun insert(medicion: Medicion) {
        medicionDao.insert(medicion)
    }

    suspend fun update(medicion: Medicion) {
        medicionDao.update(medicion)
    }

    suspend fun delete(medicion: Medicion) {
        medicionDao.delete(medicion)
    }
}