package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.AddressEntity
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface AddressEntityRepository {

    fun findAllById(ids: List<UUID>): Flow<AddressEntity>

    suspend fun findById(id: UUID): AddressEntity?
}
