package com.example.otelissue.infra.db.repositories.entity

import com.example.otelissue.infra.db.entities.StudentEntity
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface StudentEntityRepository {

    fun findAll(): Flow<StudentEntity>

    suspend fun findById(id: UUID): StudentEntity?
}
