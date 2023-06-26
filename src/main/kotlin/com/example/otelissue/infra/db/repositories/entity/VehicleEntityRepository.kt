package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.VehicleEntity
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface VehicleEntityRepository {

    fun findAllById(ids: List<UUID>): Flow<VehicleEntity>

    suspend fun findById(id: UUID): VehicleEntity?
}
